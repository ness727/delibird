package com.megamaker.loginservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseCheckUser {
    private String userId;
    private String providerId;
    private UserStatus status;
}
