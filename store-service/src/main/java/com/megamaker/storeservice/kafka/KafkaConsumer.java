package com.megamaker.storeservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamaker.storeservice.entity.Product;
import com.megamaker.storeservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class KafkaConsumer {
    private final ProductRepository productRepository;

    @KafkaListener(topics = "source_order_products", groupId = "consumerGroupId")
    public void updateQuantity(String kafkaMessage) {
        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        log.info("수량 갱신 시도");

        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 주문할 상품과 개수 가져옴
        Map<Object, Object> payload = (Map<Object, Object>) map.get("payload");
        long requestProductId = Long.parseLong(payload.get("product_id").toString());
        int requestQuantity = (int) payload.get("quantity");

        // DB에서 상품 조회
        Product foundProduct = productRepository.findById(requestProductId).orElseThrow();

        // 수량 갱신
        foundProduct.setQuantity(foundProduct.getQuantity() - requestQuantity);
        productRepository.save(foundProduct);
        log.info("수량 갱신됨");
    }
}
