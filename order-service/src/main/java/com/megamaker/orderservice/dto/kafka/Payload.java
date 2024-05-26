package com.megamaker.orderservice.dto.kafka;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Builder
public class Payload {
    private Long user_id;
    private Long store_id;
    private Long coupon_id;
    private int sum_price;
    private String request;
}
