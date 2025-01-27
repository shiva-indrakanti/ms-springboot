package com.ms.payment.service.impl;

import com.ms.payment.dto.PaymentRequest;
import com.ms.payment.entity.Payment;
import com.ms.payment.exception.InvalidPaymentRequestException;
import com.ms.payment.exception.PaymentProcessingException;
import com.ms.payment.service.IPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import com.ms.payment.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final static Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private final static String CLASS_NAME = "PaymentServiceImpl";

    @Autowired
    private PaymentRepo paymentRepo;

    @Override
    public String processPayment(PaymentRequest paymentRequest) {
        final String METHOD_NAME = "processPayment";
        logger.info("{} , {}, Method execution started.", CLASS_NAME, METHOD_NAME);

        try {
            // Validate the paymentRequest object
            validatePaymentRequest(paymentRequest);

            Payment payment = new Payment();
            payment.setTransactionId(generateTransactionID());
            payment.setOrderId(paymentRequest.getOrderId());
            payment.setUserId(paymentRequest.getUserId());
            payment.setAmount(paymentRequest.getAmount());
            payment.setPaymentMethod(paymentRequest.getPaymentMethod());
            payment.setTimestamp(LocalDateTime.now());
            payment.setStatus("SUCCESS");
            paymentRepo.save(payment);
            logger.info("{} , {}, Payment completed successfully.", CLASS_NAME, METHOD_NAME);
            return payment.getTransactionId();
        } catch (InvalidPaymentRequestException e) {
            logger.error("{} , {}, Invalid payment request: {}", CLASS_NAME, METHOD_NAME, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("{} , {}, Unexpected error occurred while processing payment : {}", CLASS_NAME, METHOD_NAME, e.getMessage(), e);
            throw new PaymentProcessingException("Error while processing payment", e);
        } finally {
            logger.info("{} , {}, Method execution completed.", CLASS_NAME, METHOD_NAME);
        }
    }

    @Override
    public Payment retrievePaymentInfo(String orderId) {
        return null;
    }

    private String generateTransactionID() {
        return "TN"+(1000000000000L + (long) (Math.random() * 9000000000000L));
    }

    // Helper method for validation
    private void validatePaymentRequest(PaymentRequest paymentRequest) {
        if (paymentRequest == null) {
            throw new InvalidPaymentRequestException("PaymentRequest is null");
        }
        if (paymentRequest.getOrderId() == null) {
            throw new InvalidPaymentRequestException("Order ID is required");
        }
        if (paymentRequest.getUserId() == null) {
            throw new InvalidPaymentRequestException("User ID is required");
        }
        if (paymentRequest.getAmount() == null || paymentRequest.getAmount() <= 0) {
            throw new InvalidPaymentRequestException("Amount must be greater than zero");
        }
        if (paymentRequest.getPaymentMethod() == null || paymentRequest.getPaymentMethod().isEmpty()) {
            throw new InvalidPaymentRequestException("Payment method is required");
        }
    }
}
