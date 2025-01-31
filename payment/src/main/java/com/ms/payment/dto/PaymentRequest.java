package com.ms.payment.dto;

public class PaymentRequest {

    private String orderNumber;
    private Double amount;
    private String paymentMethod; // e.g., CREDIT_CARD, DEBIT_CARD, UPI
    private String userName;

    // Constructors
    public PaymentRequest() {}

    public PaymentRequest(String orderNumber, Double amount, String paymentMethod, String userName) {
        this.orderNumber = orderNumber;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "orderId=" + orderNumber +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", userName=" + userName +
                '}';
    }
}
