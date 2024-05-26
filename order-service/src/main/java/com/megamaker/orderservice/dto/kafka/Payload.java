package com.megamaker.orderservice.dto.kafka;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public class Payload {
    private Long userId;
    private Long storeId;
    private Long couponId;
    private Integer sumPrice;
    private String request;
    private Timestamp updated_at;
    private Timestamp created_at;
}
