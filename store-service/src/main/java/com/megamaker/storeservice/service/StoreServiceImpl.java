package com.megamaker.storeservice.service;

import com.megamaker.storeservice.dto.store.*;
import com.megamaker.storeservice.entity.Category;
import com.megamaker.storeservice.entity.Store;
import com.megamaker.storeservice.mapper.ProductMapper;
import com.megamaker.storeservice.mapper.StoreMapper;
import com.megamaker.storeservice.repository.CategoryRepository;
import com.megamaker.storeservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


@Slf4j
@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final StoreMapper storeMapper;

    @Override
    public ResponseSaveStore save(RequestSaveStore requestSaveStore) {
        // 카테고리 조회
        Category foundCategory = categoryRepository.findById(requestSaveStore.getCategoryId()).orElseThrow();
        Store store = storeMapper.toStore(requestSaveStore);
        // 카테고리 category_id -> Category 객체 변환해서 지정
        store.setCategory(foundCategory);
        Store savedStore = storeRepository.save(store);
        return storeMapper.toResponseSaveStore(savedStore);
    }

    @Override
    public Slice<ResponseListStore> findAll(StoreSearchCondition searchCond, Pageable pageable) {
        Slice<Store> foundStores = storeRepository.findAll(searchCond, pageable);
        return foundStores.map(storeMapper::toResponseListStore);
    }

    @Override
    public ResponseStore find(Long storeId) {
        return storeMapper.toResponseStore(storeRepository.find(storeId));
    }
}
