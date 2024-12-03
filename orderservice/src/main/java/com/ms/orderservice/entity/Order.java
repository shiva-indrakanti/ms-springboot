package com.ms.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
}
