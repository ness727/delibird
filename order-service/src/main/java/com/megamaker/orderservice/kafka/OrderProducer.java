package com.megamaker.orderservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamaker.orderservice.dto.kafka.Field;
import com.megamaker.orderservice.dto.kafka.order.KafkaOrderDto;
import com.megamaker.orderservice.dto.kafka.order.OrderPayload;
import com.megamaker.orderservice.dto.kafka.Schema;
import com.megamaker.orderservice.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final static String TOPIC_NAME = "orders";

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

    public void send(OrderDto orderDto) {
        OrderPayload payload = OrderPayload.builder()
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
        kafkaTemplate.send(TOPIC_NAME, jsonToString);
    }
}