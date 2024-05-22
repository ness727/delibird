package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.product.ResponseProduct;
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
    public List<ResponseProduct> getProductListByIdList(List<Long> productIdList) {
        return productRepository.findByIdIn(productIdList).stream()
                .map(ProductMapper.INSTANCE::toResponseProduct)
                .toList();
    }
}
