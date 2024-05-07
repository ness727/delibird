package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.store.RequestSaveStore;
import com.megamaker.storeservice.dto.store.ResponseSaveStore;
import com.megamaker.storeservice.dto.store.ResponseStore;
import com.megamaker.storeservice.dto.store.StoreSearchCondition;
import com.megamaker.storeservice.entity.Category;
import com.megamaker.storeservice.entity.Store;
import com.megamaker.storeservice.mapper.StoreMapper;
import com.megamaker.storeservice.repository.CategoryRepository;
import com.megamaker.storeservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Slice<ResponseStore> getStores(StoreSearchCondition searchCond, Pageable pageable) {
        Slice<Store> foundStores = storeRepository.findAll(searchCond, pageable);
        return foundStores.map(StoreMapper.INSTANCE::toResponseStore);
    }

    @Override
    public ResponseSaveStore save(RequestSaveStore requestSaveStore) {
        Optional<Category> foundCategory = categoryRepository.findById(requestSaveStore.getCategoryId());

        //storeRepository.save()
        return null;
    }
}
