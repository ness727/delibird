package com.megamaker.storeservice.repository;

import com.megamaker.storeservice.dto.store.RequestSaveStore;
import com.megamaker.storeservice.dto.store.ResponseSaveStore;
import com.megamaker.storeservice.dto.store.ResponseStore;
import com.megamaker.storeservice.dto.store.StoreSearchCondition;
import com.megamaker.storeservice.entity.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository {
    Store save(Store store);

    Store find(Long storeId);
    Slice<Store> findAll(StoreSearchCondition searchCond, Pageable pageable);
}
