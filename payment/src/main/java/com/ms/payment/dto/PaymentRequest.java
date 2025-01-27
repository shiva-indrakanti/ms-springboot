package com.ms.payment.dto;

public class PaymentRequest {

    private Long orderId;
    private Double amount;
    private String paymentMethod; // e.g., CREDIT_CARD, DEBIT_CARD, UPI
    private Long userId;

    // Constructors
    public PaymentRequest() {}

    public PaymentRequest(Long orderId, Double amount, String paymentMethod, Long userId) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
                "orderId=" + orderId +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", userId=" + userId +
                '}';
    }
}
