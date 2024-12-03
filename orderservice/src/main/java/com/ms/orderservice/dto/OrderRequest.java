package com.ms.orderservice.dto;

import com.ms.orderservice.entity.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String skuCode;
    private Double price;
    private int quantity;
    private PaymentMode paymentMode;
}
