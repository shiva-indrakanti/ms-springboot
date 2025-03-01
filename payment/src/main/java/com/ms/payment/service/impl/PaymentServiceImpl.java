package com.ms.payment.service.impl;

import com.ms.payment.common.StringUtil;
import com.ms.payment.dto.PaymentRequest;
import com.ms.payment.dto.PaymentResponse;
import com.ms.payment.entity.Payment;
import com.ms.payment.exception.InvalidPaymentRequestException;
import com.ms.payment.exception.PaymentNotFoundException;
import com.ms.payment.exception.PaymentProcessingException;
import com.ms.payment.mapper.PaymentMapper;
import com.ms.payment.service.IPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ms.payment.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final static Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private final static String CLASS_NAME = "PaymentServiceImpl";

    @Autowired
    private PaymentRepo paymentRepo;


    /**
     * Helps in processing the payment
     * @return PaymentResponse object
     */
    @Override
    public String processPayment(PaymentRequest paymentRequest) {
        final String METHOD_NAME = "processPayment";
        logger.info("{} , {}, Method execution started.", CLASS_NAME, METHOD_NAME);

        try {
            // Validate the paymentRequest object
            validatePaymentRequest(paymentRequest);

            Payment payment = PaymentMapper.mapPaymentRequestToPaymentEntity(paymentRequest);
            payment.setTransactionId(generateTransactionID());
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


    /**
     * Helps in retrieving payment information of order number
     * @return PaymentResponse object
     */
    @Override
    public PaymentResponse retrievePaymentInfo(String transactionId) {

        //if order no is null or empty ,returning null pointer exception..
        if(!StringUtil.isEmptyOrNotNull(transactionId)){
            throw new NullPointerException("Order Id passed is null or empty. Please try again later.");
        }

        //retrieving payment record for order no, if not throwing payment related exception..
        Payment payment = paymentRepo.findByTransactionId(transactionId).orElseThrow(
                () -> new PaymentNotFoundException("Payment not found for order: " + transactionId));
        return PaymentMapper.mapPaymentEntityToPaymentResponse(payment);
    }

    private String generateTransactionID() {
        return "TN"+(1000000000000L + (long) (Math.random() * 9000000000000L));
    }

    // Helper method for validation
    private void validatePaymentRequest(PaymentRequest paymentRequest) {
        if (paymentRequest == null) {
            throw new InvalidPaymentRequestException("PaymentRequest is null");
        }
        if (paymentRequest.getUserName() == null) {
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
