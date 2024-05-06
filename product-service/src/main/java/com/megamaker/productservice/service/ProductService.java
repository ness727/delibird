package com.megamaker.productservice.service;

import com.megamaker.productservice.dto.ResponseProduct;

public interface ProductService {
    ResponseProduct getProductsByStoreId(Long storeId);
}
