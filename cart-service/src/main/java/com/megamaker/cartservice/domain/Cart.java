package com.megamaker.cartservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@RedisHash(value = "cart", timeToLive = 2592000)  // 30일
public class Cart implements Serializable {
    @Id
    private String userId;
    private String storeId;
    private List<Integer> productList;

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
