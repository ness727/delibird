package com.megamaker.storeservice.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class RequestCategory {
    private String name;
    private String image;
}
