package com.ms.productservice.service;

import com.ms.productservice.dto.ProductRequest;

public interface IProductService {
    String addProduct(ProductRequest productRequest);
}
