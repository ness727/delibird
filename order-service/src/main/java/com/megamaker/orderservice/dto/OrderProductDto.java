package com.megamaker.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class OrderProductDto {
    private Long orderId;
    private Long productId;
    private Integer quantity;
}
