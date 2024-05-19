package com.megamaker.storeservice.dto.user;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class ResponseUser {
    private String userId;
    private String regionCode;
    private String status;
}
