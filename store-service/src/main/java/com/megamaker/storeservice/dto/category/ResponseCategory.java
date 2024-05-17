package com.megamaker.storeservice.dto.category;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseCategory {
    private Integer id;
    private String name;
    private String image;
}
