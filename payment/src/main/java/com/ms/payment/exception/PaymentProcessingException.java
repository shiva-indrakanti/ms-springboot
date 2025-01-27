package com.ms.payment.exception;

public class PaymentProcessingException extends RuntimeException{
    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
