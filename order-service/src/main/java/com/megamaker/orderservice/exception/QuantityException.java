package com.megamaker.orderservice.exception;

public class QuantityException extends RuntimeException {
    public QuantityException(String message) {
        super(message);
    }

    public QuantityException(String message, Throwable cause) {
        super(message, cause);
    }
}
