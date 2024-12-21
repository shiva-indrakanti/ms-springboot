package com.ms.orderservice.service.impl;

import com.ms.orderservice.dto.OrderRequest;
import com.ms.orderservice.entity.Order;
import com.ms.orderservice.repo.OrderRepo;
import com.ms.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public String createOrder(OrderRequest orderRequest) {
        logger.info("createOrder method entry");
        String orderNumber = generateOrderNumber();
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setOrderDate(Instant.now());
        order.setOrderStatus("CREATED");
        order.setSkuCode(orderRequest.getSkuCode());
        order.setPrice(orderRequest.getPrice());
        order.setPaymentMode(orderRequest.getPaymentMode());
        order.setQuantity(orderRequest.getQuantity());

        orderRepo.save(order);
        logger.info("Created Order saved in db");
        logger.info("createOrder method exit");
        return order.getOrderNumber();
    }

    private String generateOrderNumber() {
        return "OD"+(1000000L + (long) (Math.random() * 9000000L));
    }
}
