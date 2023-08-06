package com.patika.shoppingapp.exception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException() {
        super("Insufficient stock for this product.");
    }

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
