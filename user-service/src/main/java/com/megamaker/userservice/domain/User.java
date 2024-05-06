package com.megamaker.userservice.domain;

import com.megamaker.userservice.vo.Provider;
import com.megamaker.userservice.vo.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Enumerated(value = EnumType.ORDINAL)
    private Provider provider;

    @Column(name = "provider_id")
    private String providerId;

    private String nickname;

    private String phone;

    @Column(name = "region_code")
    private String regionCode;  // 법정동 코드

    private String address;  // 구 주소

    @Column(name = "road_address")
    private String roadAddress;  // 도로명 주소

    @Enumerated(value = EnumType.ORDINAL)
    private UserStatus status;

    @Column(name = "access_token")
    private String accessToken;

}
