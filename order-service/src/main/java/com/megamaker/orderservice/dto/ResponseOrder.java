package com.megamaker.orderservice.dto;

import com.megamaker.orderservice.entity.OrderProduct;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResponseOrder {
    private Long userId;
    private Long storeId;
    private Long couponId;
    private Integer sumPrice;
    private String request;
    private List<ResponseOrderProduct> orderProductList;
}
