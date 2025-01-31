package com.ms.payment.dto;

public class PaymentRequest {

    private String orderNumber;
    private Double amount;
    private String paymentMethod; // e.g., CREDIT_CARD, DEBIT_CARD, UPI
    private Long userId;

    // Constructors
    public PaymentRequest() {}

    public PaymentRequest(String orderNumber, Double amount, String paymentMethod, Long userId) {
        this.orderNumber = orderNumber;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.userId = userId;
    }

    // Getters and Setters
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "orderId=" + orderNumber +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", userId=" + userId +
                '}';
    }
}
