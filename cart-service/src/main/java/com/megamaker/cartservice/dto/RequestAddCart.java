package com.megamaker.cartservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RequestAddCart {
    private Long storeId;
    private List<Integer> productIdList;
}
