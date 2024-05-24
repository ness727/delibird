package com.megamaker.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order extends BaseDateTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "sum_price")
    private Integer sumPrice;

    private String request;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProductList;

    public void setSumPrice(Integer sumPrice) {
        this.sumPrice = sumPrice;
    }
}
