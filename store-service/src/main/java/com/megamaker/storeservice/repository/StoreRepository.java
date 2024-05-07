package com.megamaker.storeservice.repository;

import com.megamaker.storeservice.dto.store.StoreSearchCondition;
import com.megamaker.storeservice.entity.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository {
    Slice<Store> findAll(StoreSearchCondition searchCond, Pageable pageable);
}
