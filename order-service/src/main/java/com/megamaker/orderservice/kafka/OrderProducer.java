package com.megamaker.orderservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamaker.orderservice.dto.kafka.*;
import com.megamaker.orderservice.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fieldList = List.of(new Field("bigint", true, "user_id"),
            new Field("bigint", true, "store_id"),
            new Field("bigint", true, "coupon_id"),
            new Field("integer", true, "sum_price"),
            new Field("string", true, "request"),
            new Field("timestamp", true, "updated_at"),
            new Field("timestamp", true, "created_at")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fieldList(fieldList)
            .optional(false)
            .name("orders")
            .build();

    public void send(String topic, OrderDto orderDto) {
        Payload payload = Payload.builder()
                .userId(orderDto.getUserId())
                .storeId(orderDto.getStoreId())
                .couponId(orderDto.getCouponId())
                .sumPrice(orderDto.getSumPrice())
                .request(orderDto.getRequest())
                .updated_at(new Timestamp(System.currentTimeMillis()))
                .created_at(new Timestamp(System.currentTimeMillis()))
                .build();

        KafkaOrderDto kafkaOrderDto = KafkaOrderDto.builder()
                .schema(schema)
                .payload(payload)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonToString = "";

        try {
            jsonToString = mapper.writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonToString);
        log.info("Order Producer sent data from the Order microservice: {}", kafkaOrderDto);
    }
}