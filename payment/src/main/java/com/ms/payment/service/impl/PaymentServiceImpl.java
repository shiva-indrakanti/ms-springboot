package com.ms.payment.service.impl;

import com.ms.payment.common.StringUtil;
import com.ms.payment.constants.PaymentStatus;
import com.ms.payment.dto.PaymentRequest;
import com.ms.payment.dto.PaymentResponse;
import com.ms.payment.entity.Payment;
import com.ms.payment.exception.InvalidPaymentRequestException;
import com.ms.payment.exception.PaymentNotFoundException;
import com.ms.payment.exception.PaymentProcessingException;
import com.ms.payment.service.IPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.Optional;

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
            payment.setOrderNumber(paymentRequest.getOrderNumber());
            payment.setUserId(paymentRequest.getUserId());
            payment.setAmount(paymentRequest.getAmount());
            payment.setPaymentMethod(paymentRequest.getPaymentMethod());
            payment.setTimestamp(LocalDateTime.now());
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepo.save(payment);
            logger.info("{} , {}, Payment completed successfully.", CLASS_NAME, METHOD_NAME);
            logger.info("{} , {}, Method execution completed.", CLASS_NAME, METHOD_NAME);
            return payment.getTransactionId();
        } catch (InvalidPaymentRequestException e) {
            logger.error("{} , {}, Invalid payment request: {}", CLASS_NAME, METHOD_NAME, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("{} , {}, Unexpected error occurred while processing payment : {}", CLASS_NAME, METHOD_NAME, e.getMessage(), e);
            throw new PaymentProcessingException("Error while processing payment", e);
        }
    }

    @Override
    public PaymentResponse retrievePaymentInfo(String orderNo) {

        //if order no is null or empty ,returning null pointer exception..
        if(!StringUtil.isEmptyOrNotNull(orderNo)){
            throw new NullPointerException("Order Id passed is null or empty. Please try again later.");
        }

        //retrieving payment record for order no, if not throwing payment related exception..
        Payment payment = paymentRepo.findByOrderNumber(orderNo).orElseThrow(
                () -> new PaymentNotFoundException("Payment not found for order: " + orderNo));

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setOrderNumber(payment.getOrderNumber());
        paymentResponse.setTransactionId(payment.getTransactionId());
        paymentResponse.setAmountPaid(payment.getAmount());
        paymentResponse.setStatus(payment.getStatus());
        paymentResponse.setPaymentMethod(payment.getPaymentMethod());
        paymentResponse.setTimestamp(payment.getTimestamp());
        return paymentResponse;
    }

    private String generateTransactionID() {
        return "TN"+(1000000000000L + (long) (Math.random() * 9000000000000L));
    }

    // Helper method for validation
    private void validatePaymentRequest(PaymentRequest paymentRequest) {
        if (paymentRequest == null) {
            throw new InvalidPaymentRequestException("PaymentRequest is null");
        }
        if (paymentRequest.getOrderNumber() == null) {
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
