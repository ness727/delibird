package com.megamaker.loginservice.dto;

import com.megamaker.loginservice.vo.Provider;
import com.megamaker.loginservice.vo.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestRegisterUser {
    private String userId;
    private Provider provider;
    private String providerId;
    private UserStatus status;
}
