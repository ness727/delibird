package com.megamaker.cartservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@RedisHash(value = "cart", timeToLive = 2592000)  // 30일
public class Cart implements Serializable {
    @Id
    private String userId;
    private String storeId;
    private Map<String, Integer> productQuantityMap;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setProductQuantityMap(Map<String, Integer> productQuantityMap) {
        this.productQuantityMap = productQuantityMap;
    }
}
