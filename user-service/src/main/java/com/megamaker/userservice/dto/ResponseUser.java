package com.megamaker.userservice.dto;

import com.megamaker.userservice.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseUser {
    private String userId;

    private String providerId;

    private String nickname;

    private String phone;

    private String address;

    @Enumerated(value = EnumType.STRING)
    private User.Status status;
}
