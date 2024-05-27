package com.megamaker.orderservice.exception;

public class NoProductException extends RuntimeException {
    public NoProductException(String message) {
        super(message);
    }

    public NoProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
