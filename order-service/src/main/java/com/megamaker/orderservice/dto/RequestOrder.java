package com.megamaker.orderservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class RequestOrder {
    private Long userId;
    private String storeId;
    private Map<Long, Integer> productQuantityMap;
    private String request;

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}