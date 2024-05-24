package com.megamaker.cartservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class RequestAddCart {
    private Long storeId;
    private Map<Long, Integer> productQuantityMap;
}
