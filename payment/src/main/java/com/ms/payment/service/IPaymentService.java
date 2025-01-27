package com.ms.payment.service;

import com.ms.payment.dto.PaymentRequest;
import com.ms.payment.entity.Payment;

public interface IPaymentService {

    public String processPayment(PaymentRequest paymentRequest);

    Payment retrievePaymentInfo(String orderId);
}
