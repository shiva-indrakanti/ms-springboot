package com.ms.orderservice.service;

import com.ms.orderservice.dto.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    public String createOrder(OrderRequest orderRequest);
}
