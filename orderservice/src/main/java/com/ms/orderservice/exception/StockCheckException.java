package com.ms.orderservice.exception;

public class StockCheckException extends RuntimeException {
    public StockCheckException(String message) {
        super(message);
    }

    public StockCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}