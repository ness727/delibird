package com.megamaker.orderservice.dto.kafka;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class OrderDto {
    private Long userId;
    private Long storeId;
    private Long couponId;
    private Integer sumPrice;
    private String request;
    private Timestamp updatedAt;
    private Timestamp createdAt;

    public void setSumPrice(Integer sumPrice) {
        this.sumPrice = sumPrice;
    }
}