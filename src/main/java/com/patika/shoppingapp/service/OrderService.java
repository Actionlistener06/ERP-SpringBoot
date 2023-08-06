package com.patika.shoppingapp.service;

import com.patika.shoppingapp.exception.InsufficientStockException;
import com.patika.shoppingapp.model.Invoice;
import com.patika.shoppingapp.model.Order;
import com.patika.shoppingapp.model.Product;
import com.patika.shoppingapp.model.enumaration.OrderState;
import com.patika.shoppingapp.repository.InvoiceRepository;
import com.patika.shoppingapp.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService extends BaseServiceImpl<Order, String> {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final InvoiceRepository invoiceRepository;
    private final TaxRateService taxRateService;

    public OrderService(OrderRepository orderRepository, ProductService productService, InvoiceRepository invoiceRepository, TaxRateService taxRateService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.invoiceRepository = invoiceRepository;
        this.taxRateService = taxRateService;
    }


    @Override
    protected JpaRepository<Order, String> getRepository() {
        return orderRepository;
    }
    public void processOrder(Order order) {
        boolean hasSufficientStock = productService.checkStockForOrder(order);

        if (hasSufficientStock) {

            orderRepository.save(order);

            // Update the product stock after order placement
            productService.updateStockAfterOrder(order);
        } else {
            throw new InsufficientStockException();
        }
    }
    @Transactional
    public Order createOrder(Order order) {

        return orderRepository.save(order);
    }
    @Transactional
    public void addProductToOrder(String orderId, String productId, int quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }

        // Update the order with the product and quantity
        order.getProducts().put(product, quantity);

        orderRepository.save(order);
    }

    public double calculateTotalPrice(Order order) {
        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double price = product.getPrice();
            totalPrice += price * quantity;
        }
        return totalPrice;
    }

    public double calculateTotalPriceIncludingTax(Order order) {
        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double price = product.getPrice();
            totalPrice += price * quantity;
        }

        double taxAmount = taxRateService.getTaxRate()*totalPrice;
        return totalPrice + taxAmount;
    }
    @Transactional
    public void updateOrderStatus(Order order, OrderState newStatus) {
        order.setOrderState(newStatus);
        orderRepository.save(order);
        if (order.getOrderState() != OrderState.APPROVED && newStatus == OrderState.APPROVED) {

            // Create an invoice when the order is approved
            createInvoice(order);
        }
    }

    public Order getOrderById(String orderId) {
        if( orderRepository.findById(orderId).isPresent()){
            return orderRepository.findById(orderId).get();
        }
        else throw new EntityNotFoundException("Bu id ile Order Bulunamamistir");
    }
    @Transactional
    public void createInvoice(Order approvedOrder) {
        Invoice invoice = new Invoice();
        invoice.setOrder(approvedOrder);
        invoice.setProducts( approvedOrder.getProducts().keySet());
        List<Integer> quantities = new ArrayList<>(approvedOrder.getProducts().values());
        invoice.setQuantities(quantities);
        invoice.setTotalPrice(calculateTotalPrice(approvedOrder));
        invoice.setTotalPriceWithTaxes(calculateTotalPriceIncludingTax(approvedOrder));
        invoiceRepository.save(invoice);
        approvedOrder.setOrderInvoice(invoice);
    }

    @Transactional
    public Order updateOrder(String orderId, Order updatedOrder) {
        orderRepository.findById(orderId).orElseThrow(()->new IllegalArgumentException("Bu order bulunamadi"));
        return orderRepository.save(updatedOrder);
    }
}
