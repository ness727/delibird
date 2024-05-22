package com.megamaker.storeservice.controller;

import com.megamaker.storeservice.dto.product.ResponseProduct;
import com.megamaker.storeservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    public List<ResponseProduct> getProductListByIdList(@RequestBody List<Long> productIdList) {
        return productService.getProductListByIdList(productIdList);
    }
}
