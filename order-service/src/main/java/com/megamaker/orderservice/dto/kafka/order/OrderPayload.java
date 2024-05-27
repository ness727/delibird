package com.megamaker.orderservice.dto.kafka.order;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderPayload {
    private Long user_id;
    private Long store_id;
    private Long coupon_id;
    private Integer sum_price;
    private String request;
}
