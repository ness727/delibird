package com.megamaker.orderservice.dto.product;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseProduct {
    private Long id;
    private String name;
    private String description;
    private String image;
    private Integer price;
    private Integer quantity;
}