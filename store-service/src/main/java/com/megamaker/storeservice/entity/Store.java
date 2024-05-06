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

    @Column(name = "category_id")
    private int categoryId;

    private String name;

    @Column(name = "region_1")
    private String region1;  // 시도

    @Column(name = "region_2")
    private String region2;  // 구

    @Column(name = "region_3")
    private String region3;  // 동

    @Column(name = "region_4")
    private String region4;  // 상세 주소

    private String image;
    private String tel;
    private String description;
}
