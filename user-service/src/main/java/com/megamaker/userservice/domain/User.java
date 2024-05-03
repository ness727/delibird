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
public class User extends BaseDateTimeEntity {
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

    private String address;

    @Enumerated(value = EnumType.ORDINAL)
    private UserStatus status;

}
