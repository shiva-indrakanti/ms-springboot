package com.ms.productservice.service;

import com.ms.productservice.dto.ProductRequest;
import com.ms.productservice.dto.ProductResponse;

public interface IProductService {
    String addProduct(ProductRequest productRequest);

    ProductResponse retrieveProductBySkuCode(String skuCode);
}
