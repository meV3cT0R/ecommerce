package com.vector.shop.user;

public class AddToCartException extends RuntimeException{
    public AddToCartException(String message) {
        super(message);
    }

    public AddToCartException(String message,Throwable cause) {
        super(message,cause);
    }
}
