package com.megamaker.userservice.dto;

import com.megamaker.userservice.vo.UserStatus;
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
