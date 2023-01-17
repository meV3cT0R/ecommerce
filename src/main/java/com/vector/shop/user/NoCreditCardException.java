package com.vector.shop.user;

public class NoCreditCardException extends RuntimeException{
    public NoCreditCardException(String message) {
        super(message);
    }

    public NoCreditCardException(String message,Throwable cause) {
        super(message,cause);
    }
}
