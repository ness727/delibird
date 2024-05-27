package com.megamaker.orderservice.dto.kafka.orderproduct;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderProductPayload {
    private Long order_id;
    private Long product_id;
    private Integer quantity;
}
