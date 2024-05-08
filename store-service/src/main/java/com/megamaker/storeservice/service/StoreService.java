package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.store.RequestSaveStore;
import com.megamaker.storeservice.dto.store.ResponseSaveStore;
import com.megamaker.storeservice.dto.store.ResponseStore;
import com.megamaker.storeservice.dto.store.StoreSearchCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface StoreService {
    ResponseSaveStore save(RequestSaveStore requestSaveStore);
    Slice<ResponseStore> findAll(StoreSearchCondition searchCond, Pageable pageable);

    ResponseStore find(Long storeId);
}
