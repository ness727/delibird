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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("int64", false, "user_id"),
            new Field("int64", false, "store_id"),
            new Field("int64", true, "coupon_id"),
            new Field("int32", false, "sum_price"),
            new Field("string", true, "request")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();

    public void send(String topic, OrderDto orderDto) {
        Payload payload = Payload.builder()
                .user_id(orderDto.getUserId())
                .store_id(orderDto.getStoreId())
                .coupon_id(orderDto.getCouponId())
                .sum_price(orderDto.getSumPrice())
                .request(orderDto.getRequest())
                .build();

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonToString = "";

        try {
            jsonToString = mapper.writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.debug(jsonToString);
        kafkaTemplate.send(topic, jsonToString);
        log.info("Order Producer sent data from the Order microservice: {}", kafkaOrderDto);
    }
}