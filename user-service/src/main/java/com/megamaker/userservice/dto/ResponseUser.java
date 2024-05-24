package com.megamaker.userservice.dto;

import com.megamaker.userservice.vo.UserStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseUser {
    private Long id;
    private String userId;
    // private String providerId;
    private String nickname;
    private String phone;

    @Enumerated(value = EnumType.STRING)
    private UserStatus status;

    private String regionCode;
    private String zipCode;
    private String address;
}
