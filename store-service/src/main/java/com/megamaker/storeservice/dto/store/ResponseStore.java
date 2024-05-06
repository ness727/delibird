package com.megamaker.storeservice.dto.store;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseStore {
    private String name;
    private String region1;  // 시도
    private String region2;  // 구
    private String region3;  // 동
    private String region4;  // 상세 주소
    private String image;
    private String tel;
    private String description;
}
