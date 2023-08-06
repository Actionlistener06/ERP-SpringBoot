package com.patika.shoppingapp.service;

import com.patika.shoppingapp.exception.InsufficientStockException;
import com.patika.shoppingapp.model.Order;
import com.patika.shoppingapp.model.Product;
import com.patika.shoppingapp.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProductService extends BaseServiceImpl<Product, String> {
    @Override
    protected JpaRepository<Product, String> getRepository() {
        return productRepository;
    }
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }



    // Method to perform a stock check before processing an order
    @Transactional
    public boolean checkStockForOrder(Order order) {
        for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
            Product product = entry.getKey();
            int orderedQuantity = entry.getValue();
            int availableStock = product.getStock();

            if (orderedQuantity > availableStock) {
                return false; // Stock is insufficient for the order
            }
        }

        return true; // Stock is sufficient for the order
    }

    // Method to update the product's stock after an order is placed
    @Transactional
    public void updateStockAfterOrder(Order order) {
        for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
            Product product = entry.getKey();
            int orderedQuantity = entry.getValue();
            int availableStock = product.getStock();

            if (orderedQuantity > availableStock) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            product.setStock(availableStock - orderedQuantity);
            productRepository.save(product);
        }
    }

    @Transactional
    public Product createProduct(Product product) {

     return productRepository.save(product);
    }
    @Transactional
    public void deleteProduct(String productId) {
        // Check if the product with the given ID exists
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        // Delete the product from the database
        productRepository.delete(product);
    }

    public Product updateProduct(String productId, Product product) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
        return productRepository.save(product);

    }
}
