package com.megamaker.orderservice.feignclient;

import com.megamaker.orderservice.dto.product.ResponseProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@FeignClient("store-service")
public interface StoreClient {
    @PostMapping("/products")
    List<ResponseProduct> getProductList(List<Long> productIdList);

    @PostMapping("/products/update")
    ResponseEntity updateQuantity(@RequestBody Map<Long, Integer> quantityMap, @RequestHeader("Auth") String token);
}