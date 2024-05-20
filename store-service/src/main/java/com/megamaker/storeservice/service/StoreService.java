package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.store.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;


public interface StoreService {
    ResponseSaveStore save(RequestSaveStore requestSaveStore);
    Slice<ResponseListStore> findAll(StoreSearchCondition searchCond, Pageable pageable);

    ResponseStore find(Long storeId);
}
