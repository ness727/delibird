package com.megamaker.storeservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "stores")
@Entity
public class Store {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    @Column(name = "region_code")
    private String regionCode;  // 법정동 코드

    private String address;  // 구 주소

    @Column(name = "road_address")
    private String roadAddress;  // 도로명 주소

    private String image;
    private String tel;
    private String description;

    public void setCategory(Category category) {
        this.category = category;
    }
}
