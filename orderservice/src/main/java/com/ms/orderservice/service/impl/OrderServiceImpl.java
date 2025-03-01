package com.ms.orderservice.service.impl;

import com.ms.orderservice.dto.OrderRequest;
import com.ms.orderservice.dto.PaymentRequest;
import com.ms.orderservice.dto.ProductResponse;
import com.ms.orderservice.entity.Order;
import com.ms.orderservice.exception.OrderException;
import com.ms.orderservice.exception.PaymentException;
import com.ms.orderservice.exception.StockCheckException;
import com.ms.orderservice.repo.OrderRepo;
import com.ms.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String createOrder(OrderRequest orderRequest) {
        logger.info("Class - OrderServiceImpl : createOrder method entry");

        // Check stock availability
        boolean isStockAvailable;
        try {
            isStockAvailable = checkStock(orderRequest.getSkuCode(), orderRequest.getQuantity());
        } catch (StockCheckException e) {
            logger.error("Stock check failed: {}", e.getMessage(), e);
            throw new OrderException("Order creation failed due to stock check error: " + e.getMessage());
        }

        if (!isStockAvailable) {
            throw new RuntimeException("Insufficient stock for SKU: " + orderRequest.getSkuCode());
        }

        //constructing payment request.
        String transactionId;
        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setPaymentMethod("UPI");
            paymentRequest.setAmount(orderRequest.getPrice());
            paymentRequest.setUserName("Shiva");
            transactionId = initiatePayment(paymentRequest);
        } catch (PaymentException e) {
            logger.error("Payment failed: {}", e.getMessage());
            throw new OrderException("Order creation failed due to payment error: " + e.getMessage());
        }

        String orderNumber = generateOrderNumber();
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setOrderDate(Instant.now());
        order.setOrderStatus("CREATED");
        order.setSkuCode(orderRequest.getSkuCode());
        order.setPrice(orderRequest.getPrice());
        order.setPaymentMode(orderRequest.getPaymentMode());
        order.setTransactionId(transactionId);
        order.setQuantity(orderRequest.getQuantity());

        orderRepo.save(order);
        //implement logic to reduce stock of used quantity from product service..
        boolean reduced = decreaseUtilisedQuantity(orderRequest.getSkuCode(),orderRequest.getQuantity());
        if(!reduced){
            throw new StockCheckException("Failed to reduce stock for SKU: " + orderRequest.getSkuCode());
        }

        logger.info("Created Order saved in db");
        logger.info("Class - OrderServiceImpl : createOrder method exit");
        return order.getOrderNumber();
    }

    private boolean decreaseUtilisedQuantity(String skuCode,int quantity) {
        String url = "http://localhost:8081/api/products/product/quantity/{skuCode}/reduce?quantity={quantity}";

        Map<String, Object> params = new HashMap<>();
        params.put("skuCode", skuCode);
        params.put("quantity", quantity);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, null, Void.class, params);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Stock reduced successfully for SKU: {}", skuCode);
                return true;
            } else {
                throw new StockCheckException("Failed to reduce stock for SKU: " + skuCode);
            }
        } catch (Exception e) {
            throw new StockCheckException("Error occurred while reducing stock for SKU: " + skuCode, e);
        }
    }

    private String initiatePayment(PaymentRequest paymentRequest) {
        String url = "http://localhost:8083/api/payments/initiate-payment";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, paymentRequest, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                logger.info("Payment successful. Transaction ID: {}", response.getBody());
                return response.getBody();
            } else {
                throw new PaymentException("Payment failed. Status Code: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            logger.error("Error while calling payment API: {}", e.getMessage(), e);
            throw new PaymentException("Payment request failed due to API error");
        }
    }

    private boolean checkStock(String skuCode, int quantity) {
        logger.info("Checking stock starting in Order service CheckStock method");
        try {
            String url = "http://localhost:8081/api/products/product/"+skuCode;
            ResponseEntity<ProductResponse> response = restTemplate.getForEntity(url, ProductResponse.class);
            if (response.getBody() != null) {
                int availableStock = response.getBody().getAvailableStock();
                return availableStock > quantity;
            } else {
                throw new StockCheckException("Response body is null for SKU: " + skuCode);
            }
        } catch (RestClientException e) {
            throw new StockCheckException("Failed to check stock for SKU: " +skuCode,e);
        }
    }

    private String generateOrderNumber() {
        return "OD"+(1000000L + (long) (Math.random() * 9000000L));
    }
}
