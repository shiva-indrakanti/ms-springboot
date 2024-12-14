package com.ms.productservice.service.impl;

import com.ms.productservice.dto.ProductRequest;
import com.ms.productservice.dto.ProductResponse;
import com.ms.productservice.entity.Product;
import com.ms.productservice.repo.ProductRepo;
import com.ms.productservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public String addProduct(ProductRequest productRequest) {
        if(productRepo.findBySkuCode(productRequest.getSkuCode()).isPresent()){
            throw new RuntimeException("Product "+ productRequest.getSkuCode()+" is Already exists ");
        }
        Product product = new Product();
        product.setSkuCode(productRequest.getSkuCode());
        product.setProductName(productRequest.getProductName());
        product.setAvailableStock(productRequest.getAvailableStock());
        product.setPrice(productRequest.getPrice());
        productRepo.save(product);
        return product.getSkuCode();
    }

    @Override
    public ProductResponse retrieveProductBySkuCode(String skuCode) {
        Product product = productRepo.findBySkuCode(skuCode).orElseThrow(()->
                   new RuntimeException("Product Not Found for SKU_CODE : "+skuCode)
                );
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductName(product.getProductName());
        productResponse.setSkuCode(product.getSkuCode());
        productResponse.setPrice(product.getPrice());
        productResponse.setAvailableStock(product.getAvailableStock());
        return productResponse;
    }
}
