package com.megamaker.userservice.dto;

import com.megamaker.userservice.domain.User;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestRegisterUser {
    private String userId;
    private String providerId;
    private User.Status status;
}
