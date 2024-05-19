package com.megamaker.storeservice.dto.store;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseStore {
    private String name;
    private Integer categoryId;
    private Integer deliveryFees;
    private String address;  // 구 주소
    private String roadAddress;  // 도로명 주소
    private String image;
    private String tel;
    private String description;
}
