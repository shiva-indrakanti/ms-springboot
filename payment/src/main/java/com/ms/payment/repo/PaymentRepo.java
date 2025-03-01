package com.ms.payment.repo;

import com.ms.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment,Long> {
    Optional<Payment> findByTransactionId(String orderNumber);
}
