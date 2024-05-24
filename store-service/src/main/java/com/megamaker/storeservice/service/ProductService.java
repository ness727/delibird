package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.product.ResponseProduct;

import java.util.List;

public interface ProductService {
    List<ResponseProduct> getProductList(List<Long> productIdList);
}
