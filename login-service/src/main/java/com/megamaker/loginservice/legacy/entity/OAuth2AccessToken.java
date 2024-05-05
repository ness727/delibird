package com.megamaker.loginservice.legacy.entity;

import lombok.*;

@Builder
@Getter
public class OAuth2AccessToken {
    private String userId;
    private String token;
    private Long timeToLive;
}
