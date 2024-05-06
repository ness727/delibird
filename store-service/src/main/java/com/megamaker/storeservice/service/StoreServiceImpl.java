package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.store.ResponseStore;
import com.megamaker.storeservice.entity.Store;
import com.megamaker.storeservice.mapper.StoreMapper;
import com.megamaker.storeservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    @Override
    public Slice<ResponseStore> getStoresByCategory(String regionCode, Integer categoryId, Pageable pageable) {
        Slice<Store> foundStores = storeRepository.findByRegionCodeAndCategoryId(regionCode, categoryId, pageable);
        return foundStores.map(StoreMapper.INSTANCE::toResponseStore);
    }
}
