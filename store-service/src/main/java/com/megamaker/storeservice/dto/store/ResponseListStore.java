package com.megamaker.storeservice.dto.store;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseListStore {
    private Long id;
    private String name;
    private Integer deliveryFees;
    private String logoImage;
}
