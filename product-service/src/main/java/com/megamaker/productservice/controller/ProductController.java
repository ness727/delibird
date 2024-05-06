package com.megamaker.productservice.controller;

import com.megamaker.productservice.dto.ResponseProduct;
import com.megamaker.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{storeId}")
    public ResponseEntity<ResponseProduct> getProductsByStoreId(@PathVariable Long storeId) {
        ResponseProduct result = productService.getProductsByStoreId(storeId);

        return ResponseEntity.ok(result);
    }

}
