package com.vector.shop.user;

public class ProductNotInStockException extends RuntimeException{
    public ProductNotInStockException(String message) {
        super(message);
    }

    public ProductNotInStockException(String message,Throwable cause) {
        super(message,cause);
    }
}
