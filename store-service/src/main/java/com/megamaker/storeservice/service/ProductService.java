package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.product.ResponseProduct;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ResponseProduct> getProductList(List<Long> productIdList);
    void updateQuantity(@RequestBody Map<Long, Integer> quantityMap);
}
