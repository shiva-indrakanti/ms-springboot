package com.ms.payment.mapper;

import com.ms.payment.constants.PaymentStatus;
import com.ms.payment.dto.PaymentRequest;
import com.ms.payment.dto.PaymentResponse;
import com.ms.payment.entity.Payment;

import java.time.LocalDateTime;

public class PaymentMapper {
    public static Payment mapPaymentRequestToPaymentEntity(PaymentRequest paymentRequest) {
        Payment paymentObj = new Payment();
        paymentObj.setOrderNumber(paymentRequest.getOrderNumber());
        paymentObj.setUserName(paymentRequest.getUserName());
        paymentObj.setAmount(paymentRequest.getAmount());
        paymentObj.setPaymentMethod(paymentRequest.getPaymentMethod());
        paymentObj.setTimestamp(LocalDateTime.now());
        paymentObj.setStatus(PaymentStatus.SUCCESS);
        return paymentObj;
    }

    public static PaymentResponse   mapPaymentEntityToPaymentResponse(Payment payment){
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setOrderNumber(payment.getOrderNumber());
        paymentResponse.setTransactionId(payment.getTransactionId());
        paymentResponse.setUserName(payment.getUserName());
        paymentResponse.setAmountPaid(payment.getAmount());
        paymentResponse.setStatus(payment.getStatus());
        paymentResponse.setPaymentMethod(payment.getPaymentMethod());
        paymentResponse.setTimestamp(payment.getTimestamp());
        return paymentResponse;
    }
}
