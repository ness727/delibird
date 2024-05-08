package com.megamaker.storeservice.dto.category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestSaveCategory {
    private String name;
    private String image;
}
