package com.ms.orderservice.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name="orders",schema = "microservice")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String orderNumber;
    private Instant orderDate;
    private String orderStatus;
    private String skuCode;
    private Double price;
    private int quantity;
    private PaymentMode paymentMode;

    public Order() {
    }

    public Order(PaymentMode paymentMode, int quantity, Double price, String skuCode, String orderStatus, Instant orderDate, String orderNumber) {
        this.paymentMode = paymentMode;
        this.quantity = quantity;
        this.price = price;
        this.skuCode = skuCode;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderNumber = orderNumber;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
