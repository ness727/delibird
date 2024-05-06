package com.megamaker.productservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseProduct {
    private String name;
    private String description;
    private String image;
    private int price;
    private int quantity;
}
