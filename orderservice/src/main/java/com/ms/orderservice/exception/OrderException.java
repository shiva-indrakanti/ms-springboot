package com.ms.orderservice.exception;

public class OrderException extends RuntimeException{
    public OrderException(String message) {
        super(message);
    }
}
