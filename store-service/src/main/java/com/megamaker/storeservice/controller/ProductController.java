package com.megamaker.storeservice.controller;

import com.megamaker.storeservice.dto.product.ResponseProduct;
import com.megamaker.storeservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public List<ResponseProduct> getProductList(@RequestBody List<Long> productIdList) {
        return productService.getProductList(productIdList);
    }

    @PostMapping("/update")
    public ResponseEntity updateQuantity(@RequestBody Map<Long, Integer> quantityMap) {
        try {
            productService.updateQuantity(quantityMap);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
