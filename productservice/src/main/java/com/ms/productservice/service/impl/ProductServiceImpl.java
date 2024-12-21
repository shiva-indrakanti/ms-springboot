package com.ms.productservice.service.impl;

import com.ms.productservice.dto.ProductRequest;
import com.ms.productservice.dto.ProductResponse;
import com.ms.productservice.entity.Product;
import com.ms.productservice.repo.ProductRepo;
import com.ms.productservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ProductServiceImpl implements IProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductRepo productRepo;

    @Override
    public String addProduct(ProductRequest productRequest) {
        logger.info("Class - ProductServiceImpl : addProduct method entry");
        if(productRepo.findBySkuCode(productRequest.getSkuCode()).isPresent()){
            throw new RuntimeException("Product "+ productRequest.getSkuCode()+" is Already exists ");
        }
        Product product = new Product();
        product.setSkuCode(productRequest.getSkuCode());
        product.setProductName(productRequest.getProductName());
        product.setAvailableStock(productRequest.getAvailableStock());
        product.setPrice(productRequest.getPrice());
        productRepo.save(product);
        logger.info("New product saved successfully");
        logger.info("Class - ProductServiceImpl : addProduct method exit");
        return product.getSkuCode();
    }

    @Override
    public ProductResponse retrieveProductBySkuCode(String skuCode) {
        logger.info("Class - ProductServiceImpl : retrieveProductBySkuCode method entry");
        Product product = productRepo.findBySkuCode(skuCode).orElseThrow(()->
                   new RuntimeException("Product Not Found for SKU_CODE : "+skuCode)
                );
        logger.info("Product retrieved..");
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductName(product.getProductName());
        productResponse.setSkuCode(product.getSkuCode());
        productResponse.setPrice(product.getPrice());
        productResponse.setAvailableStock(product.getAvailableStock());
        logger.info("Class - ProductServiceImpl : retrieveProductBySkuCode method exit");
        return productResponse;
    }

    @Override
    public void reduceAvailableStock(String skuCode, int quantity) {
        logger.info("Class - ProductServiceImpl : reduceAvailableStock method entry");
        Product product = productRepo.findBySkuCode(skuCode).orElseThrow(()->
                new RuntimeException("Product Not Found for SKU_CODE : "+skuCode)
        );
        if(product.getAvailableStock() < quantity){
            throw new RuntimeException("Not having enough stock for the product : "+skuCode);
        }

        product.setAvailableStock(product.getAvailableStock() - quantity);
        productRepo.save(product);
        logger.info("Stock updated");
        logger.info("Class - ProductServiceImpl : reduceAvailableStock method exit");
    }
}
