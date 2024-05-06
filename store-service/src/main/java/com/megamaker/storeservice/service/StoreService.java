package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.store.ResponseStore;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface StoreService {
    Slice<ResponseStore> getStoresByCategory(String regionCode, Integer categoryId, Pageable pageable);
}
