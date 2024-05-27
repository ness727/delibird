package com.megamaker.orderservice.repository;

import com.megamaker.orderservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r left join Order o on r.id = o.id where o.storeId = :storeId")
    List<Review> findAllByStoreId(@Param("storeId") Long storeId);
}
