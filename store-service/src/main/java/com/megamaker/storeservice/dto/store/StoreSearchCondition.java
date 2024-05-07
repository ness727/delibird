package com.megamaker.storeservice.dto.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreSearchCondition {
    String regionCode;
    Integer CategoryId;
}
