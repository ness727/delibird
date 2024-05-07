package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.store.RequestSaveStore;
import com.megamaker.storeservice.dto.store.ResponseSaveStore;
import com.megamaker.storeservice.dto.store.ResponseStore;
import com.megamaker.storeservice.dto.store.StoreSearchCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface StoreService {
    Slice<ResponseStore> findAll(StoreSearchCondition searchCond, Pageable pageable);
    ResponseSaveStore save(RequestSaveStore requestSaveStore);
}
