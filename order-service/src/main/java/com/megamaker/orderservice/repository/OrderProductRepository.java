package com.megamaker.orderservice.repository;

import com.megamaker.orderservice.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
