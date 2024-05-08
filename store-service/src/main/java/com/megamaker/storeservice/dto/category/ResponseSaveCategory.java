package com.megamaker.storeservice.dto.category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseSaveCategory {
    private Integer id;
    private String name;
}
