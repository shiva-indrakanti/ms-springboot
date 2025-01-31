package com.ms.payment.dto;

import com.ms.payment.constants.PaymentStatus;

import java.time.LocalDateTime;


public class PaymentResponse {
    private String orderNumber;
    private String transactionId;
    private String userName;
    private Double amountPaid;
    private String paymentMethod;
    private PaymentStatus status; // e.g., INITIATED, COMPLETED, FAILED
    private LocalDateTime timestamp;

    // Constructors
    public PaymentResponse() {}

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "paymentId=" + transactionId +
                ", orderNumber=" + orderNumber +
                ", amount=" + amountPaid +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
