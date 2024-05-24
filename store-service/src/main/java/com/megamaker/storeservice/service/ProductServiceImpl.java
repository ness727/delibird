package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.product.ResponseProduct;
import com.megamaker.storeservice.entity.Product;
import com.megamaker.storeservice.mapper.ProductMapper;
import com.megamaker.storeservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ResponseProduct> getProductList(List<Long> productIdList) {
        List<Product> foundProductList = productRepository.findByIdIn(productIdList);
        return foundProductList.stream().map(ProductMapper.INSTANCE::toResponseProduct).toList();
    }
}
