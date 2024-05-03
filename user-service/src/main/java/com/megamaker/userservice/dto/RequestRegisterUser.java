package com.megamaker.userservice.dto;

import com.megamaker.userservice.vo.Provider;
import com.megamaker.userservice.vo.UserStatus;
import lombok.*;


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
