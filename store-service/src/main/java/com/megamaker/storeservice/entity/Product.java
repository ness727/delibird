package com.megamaker.storeservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
@Entity
public class Product extends BaseDateTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String image;
    private Integer price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
