package com.megamaker.storeservice.dto.store;

import com.megamaker.storeservice.dto.product.ResponseProduct;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ResponseStore {
    private Long id;
    private String name;
    private Integer categoryId;
    private Integer deliveryFees;
    private String address;  // 구 주소
    private String roadAddress;  // 도로명 주소
    private String logoImage;
    private String image1;
    private String image2;
    private String image3;
    private String tel;
    private String description;

    private List<ResponseProduct> productList;
}
