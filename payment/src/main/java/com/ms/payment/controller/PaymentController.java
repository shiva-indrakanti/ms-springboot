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

    @GetMapping("/payment-info/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetails(@RequestParam(name = "orderId") String orderId){
        Payment paymentData = iPaymentService.retrievePaymentInfo(orderId);
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentId(paymentData.getPaymentId());
        paymentResponse.setOrderId(paymentData.getOrderId());
        paymentResponse.setAmount(paymentData.getAmount());
        paymentResponse.setPaymentMethod(paymentData.getPaymentMethod());
        paymentResponse.setStatus(paymentData.getStatus());
        paymentResponse.setTimestamp(paymentData.getTimestamp());
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
