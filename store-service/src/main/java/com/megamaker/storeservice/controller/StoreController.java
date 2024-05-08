package com.megamaker.storeservice.controller;

import com.megamaker.storeservice.dto.store.RequestSaveStore;
import com.megamaker.storeservice.dto.store.ResponseSaveStore;
import com.megamaker.storeservice.dto.store.ResponseStore;
import com.megamaker.storeservice.dto.store.StoreSearchCondition;
import com.megamaker.storeservice.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@RequestMapping("/stores")
@RequiredArgsConstructor
@RestController
public class StoreController {
    private final StoreService storeService;

    @PostMapping
    public ResponseSaveStore save(@RequestBody RequestSaveStore requestSaveStore) {
        return storeService.save(requestSaveStore);
    }

    // 카테고리로 매장 검색, 쿼리 파라미터에 page, size, sort 포함
    @GetMapping
    public Slice<ResponseStore> findAll(@ModelAttribute StoreSearchCondition searchCond,
                                                    Pageable pageable) {
        return storeService.findAll(searchCond, pageable);
    }

    @GetMapping("/{storeId}")
    public ResponseStore find(@PathVariable Long storeId) {
        return storeService.find(storeId);
    }
}
