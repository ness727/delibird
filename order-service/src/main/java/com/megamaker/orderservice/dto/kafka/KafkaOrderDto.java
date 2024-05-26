package com.megamaker.orderservice.dto.kafka;

import lombok.Builder;

import java.io.Serializable;

@Builder
public class KafkaOrderDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
