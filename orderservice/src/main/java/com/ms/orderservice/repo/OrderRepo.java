package com.ms.orderservice.repo;

import com.ms.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
