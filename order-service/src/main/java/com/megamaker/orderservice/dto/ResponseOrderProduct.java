package com.megamaker.orderservice.dto;

import com.megamaker.orderservice.entity.Order;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseOrderProduct {
    private Long productId;
    private Integer quantity;
}
