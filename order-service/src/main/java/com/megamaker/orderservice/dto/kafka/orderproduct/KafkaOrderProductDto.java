package com.megamaker.orderservice.dto.kafka.orderproduct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.megamaker.orderservice.dto.kafka.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class KafkaOrderProductDto implements Serializable {
    private Schema schema;

    @JsonProperty("payload")
    private OrderProductPayload orderProductPayload;
}
