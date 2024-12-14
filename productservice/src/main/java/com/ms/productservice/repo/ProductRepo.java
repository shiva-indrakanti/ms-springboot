package com.ms.productservice.repo;

import com.ms.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,Long> {
    Optional<Product> findBySkuCode(String skuCode);
}
