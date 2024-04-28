package com.megamaker.userservice.domain;

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

    @Column(name = "provider_id")
    private String providerId;

    private String nickname;

    private String phone;

    private String address;

    @Enumerated(value = EnumType.ORDINAL)
    private Status status;

    public enum Status {
        ADMIN, MANAGER, USER
    }
}
