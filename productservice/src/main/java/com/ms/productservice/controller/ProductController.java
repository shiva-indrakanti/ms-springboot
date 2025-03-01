package com.ms.productservice.controller;

import com.ms.productservice.dto.ProductRequest;
import com.ms.productservice.dto.ProductResponse;
import com.ms.productservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest){
        String skuCode = iProductService.addProduct(productRequest);
        return new ResponseEntity<String>(skuCode, HttpStatus.CREATED);
    }

    @GetMapping("/product/{skuCode}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("skuCode") String skuCode){
        ProductResponse productResponse =  iProductService.retrieveProductBySkuCode(skuCode);
        return new ResponseEntity<ProductResponse>(productResponse,HttpStatus.OK);
    }

        @PutMapping("/product/quantity/{skuCode}/reduce")
    public ResponseEntity<Void> decrementQuantity(@PathVariable("skuCode") String skuCode,@RequestParam int quantity){
        iProductService.reduceAvailableStock(skuCode,quantity);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
 }
