package com.megamaker.userservice.dto;

import com.megamaker.userservice.vo.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestUpdateUser {
    private String nickname;
    private String phone;

    private String regionCode;
    private String zipCode;
    private String address;
}
