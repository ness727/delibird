package com.megamaker.storeservice.controller;

import com.megamaker.storeservice.dto.store.ResponseStore;
import com.megamaker.storeservice.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;

@RequestMapping("/stores")
@RequiredArgsConstructor
@RestController
public class StoreController {
    private final StoreService storeService;

    // 카테고리로 매장 검색, 쿼리 파라미터에 page, size, sort 포함
    @GetMapping
    public Slice<ResponseStore> getStoresByCategory(@RequestParam("region_2") String region2,
                                                                    @RequestParam int categoryId, Pageable pageable) {
        return storeService.getStoresByCategory(region2, categoryId, pageable);
    }
}
