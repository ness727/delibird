package com.megamaker.storeservice.dto.store;

import lombok.*;

@Builder
@ToString
@Getter
public class StoreSearchCondition {
    Integer categoryId;
    String storeName;
    String regionCode;

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
}
