package com.patika.shoppingapp.controller;

import com.patika.shoppingapp.model.Order;
import com.patika.shoppingapp.model.Product;
import com.patika.shoppingapp.model.enumaration.OrderState;
import com.patika.shoppingapp.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}/total-price")
    public ResponseEntity<Double> calculateTotalPrice(@PathVariable String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        double totalPrice = orderService.calculateTotalPrice(order);
        return new ResponseEntity<>(totalPrice, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderState newStatus) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        orderService.updateOrderStatus(order, newStatus);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable String orderId, @RequestBody Order updatedOrder) {
        Order existingOrder = orderService.getOrderById(orderId);
        if (existingOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Order updatedOrderResult = orderService.updateOrder(orderId, updatedOrder);
        return new ResponseEntity<>(updatedOrderResult, HttpStatus.OK);
    }

    @PostMapping("/{orderId}/addProduct")
    public ResponseEntity<Order> addProductToOrder(@PathVariable String orderId, @RequestBody Product productToAdd, @RequestParam int quantity) {
        Order existingOrder = orderService.getOrderById(orderId);
        if (existingOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderService.addProductToOrder(orderId, productToAdd.getId(), quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}