package com.ms.orderservice.controller;

import com.ms.orderservice.dto.OrderRequest;
import com.ms.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest){
        String orderNumber = orderService.createOrder(orderRequest);
        return new ResponseEntity<String>(orderNumber,HttpStatus.OK);
    }
}
