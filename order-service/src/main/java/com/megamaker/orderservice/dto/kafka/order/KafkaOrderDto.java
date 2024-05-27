package com.megamaker.orderservice.dto.kafka.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.megamaker.orderservice.dto.kafka.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class KafkaOrderDto implements Serializable {
    private Schema schema;

    @JsonProperty("payload")
    private OrderPayload orderPayload;
}
