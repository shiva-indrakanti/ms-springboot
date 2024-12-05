package com.ms.productservice.service.impl;

import com.ms.productservice.dto.ProductRequest;
import com.ms.productservice.entity.Product;
import com.ms.productservice.repo.ProductRepo;
import com.ms.productservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public String addProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setSkuCode(productRequest.getSkuCode());
        product.setProductName(productRequest.getProductName());
        product.setAvailableStock(productRequest.getAvailableStock());
        product.setPrice(productRequest.getPrice());
        productRepo.save(product);
        return product.getSkuCode();
    }
}
