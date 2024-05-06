package com.megamaker.storeservice.repository;

import com.megamaker.storeservice.entity.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Slice<Store> findByRegion2AndCategoryId(String region2, int categoryId, Pageable pageable);
}
