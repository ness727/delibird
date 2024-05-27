package com.megamaker.orderservice.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megamaker.orderservice.dto.OrderProductDto;
import com.megamaker.orderservice.dto.kafka.Field;
import com.megamaker.orderservice.dto.kafka.Schema;
import com.megamaker.orderservice.dto.kafka.orderproduct.KafkaOrderProductDto;
import com.megamaker.orderservice.dto.kafka.orderproduct.OrderProductPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProductProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final static String TOPIC_NAME = "order_products";

    List<Field> fields = Arrays.asList(
            new Field("int64", false, "order_id"),
            new Field("int64", false, "product_id"),
            new Field("int32", false, "quantity")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("order_products")
            .build();

    public void send(List<OrderProductDto> orderProductDtoList) {
        for (OrderProductDto orderProductDto : orderProductDtoList) {
            OrderProductPayload payload = OrderProductPayload.builder()
                    .order_id(orderProductDto.getOrderId())
                    .product_id(orderProductDto.getProductId())
                    .quantity(orderProductDto.getQuantity())
                    .build();

            KafkaOrderProductDto kafkaOrderProductDto = new KafkaOrderProductDto(schema, payload);

            ObjectMapper mapper = new ObjectMapper();
            String jsonToString = "";

            try {
                jsonToString = mapper.writeValueAsString(kafkaOrderProductDto);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            kafkaTemplate.send(TOPIC_NAME, jsonToString);
        }
    }
}