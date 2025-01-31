package com.ms.payment.controller;


import com.ms.payment.dto.PaymentRequest;
import com.ms.payment.dto.PaymentResponse;
import com.ms.payment.entity.Payment;
import com.ms.payment.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private IPaymentService iPaymentService;

    @PostMapping("/initiate-payment")
    public ResponseEntity<String> initiatePayment(@RequestBody PaymentRequest paymentRequest) {
        String payId = iPaymentService.processPayment(paymentRequest);
        return new ResponseEntity<>(payId, HttpStatus.CREATED);
    }

    @GetMapping("/payment-info/{orderNo}")
    public ResponseEntity<PaymentResponse> getPaymentDetails(@PathVariable(name = "orderNo") String orderNo){
        PaymentResponse paymentData = iPaymentService.retrievePaymentInfo(orderNo);
        return new ResponseEntity<>(paymentData, HttpStatus.OK);
    }
}
