package com.megamaker.orderservice.feignclient;

import com.megamaker.orderservice.dto.product.ResponseProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient("store-service")
public interface StoreClient {
    @PostMapping("/products")
    List<ResponseProduct> getProductList(List<Long> productIdList);
}