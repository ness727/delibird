package com.megamaker.storeservice.dto.store;

import com.megamaker.storeservice.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestSaveStore {
    private Integer categoryId;
    private String name;
    private String regionCode;  // 법정동 코드
    private String address;  // 구 주소
    private String roadAddress;  // 도로명 주소
    private String image;
    private String tel;
    private String description;
}
