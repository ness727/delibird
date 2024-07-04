package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.product.ResponseProduct;
import com.megamaker.storeservice.entity.Product;
import com.megamaker.storeservice.mapper.ProductMapper;
import com.megamaker.storeservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ResponseProduct> getProductList(List<Long> productIdList) {
        List<Product> foundProductList = productRepository.findByIdIn(productIdList);
        return foundProductList.stream().map(productMapper::toResponseProduct).toList();
    }

    @Override
    public void updateQuantity(Map<Long, Integer> quantityMap) {
        for (Map.Entry<Long, Integer> quantity : quantityMap.entrySet()) {
            // DB에서 상품 조회
            Product foundProduct = productRepository.findById(quantity.getKey()).orElseThrow();

            // 수량 갱신
            foundProduct.setQuantity(foundProduct.getQuantity() - quantity.getValue());
            productRepository.save(foundProduct);
        }
        log.info("수량 갱신됨");
    }
}
