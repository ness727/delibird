package com.megamaker.storeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "stores")
@Entity
public class Store extends BaseDateTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ColumnDefault("0")
    private Integer deliveryFees;

    @Column(name = "region_code")
    private String regionCode;  // 법정동 코드

    private String address;  // 구 주소

    @Column(name = "road_address")
    private String roadAddress;  // 도로명 주소

    @Column(name = "logo_image")
    private String logoImage;
    private String image1;
    private String image2;
    private String image3;

    private String tel;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "store")
    private List<Product> productList;

    public void setCategory(Category category) {
        this.category = category;
    }
}
