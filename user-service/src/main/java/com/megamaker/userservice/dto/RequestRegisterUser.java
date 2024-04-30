package com.megamaker.userservice.dto;

import com.megamaker.userservice.domain.User;
import com.megamaker.userservice.domain.UserStatus;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestRegisterUser {
    private String userId;
    private String providerId;
    private UserStatus status;
}