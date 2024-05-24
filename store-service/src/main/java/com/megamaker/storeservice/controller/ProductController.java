package com.megamaker.storeservice.controller;

import com.megamaker.storeservice.dto.product.ResponseProduct;
import com.megamaker.storeservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public List<ResponseProduct> getProductList(@RequestBody List<Long> productIdList) {
        return productService.getProductList(productIdList);
    }
}
