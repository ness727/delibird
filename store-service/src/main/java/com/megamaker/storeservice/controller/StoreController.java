package com.megamaker.storeservice.controller;

import com.megamaker.storeservice.dto.store.RequestSaveStore;
import com.megamaker.storeservice.dto.store.ResponseSaveStore;
import com.megamaker.storeservice.dto.store.ResponseStore;
import com.megamaker.storeservice.dto.store.StoreSearchCondition;
import com.megamaker.storeservice.security.UserAuthentication;
import com.megamaker.storeservice.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RequestMapping("/stores")
@RequiredArgsConstructor
@RestController
public class StoreController {
    private final StoreService storeService;

    @PostMapping
    public ResponseSaveStore save(@RequestBody RequestSaveStore requestSaveStore) {
        return storeService.save(requestSaveStore);
    }

    /**
     * 카테고리로 매장 검색, 쿼리 파라미터에 page, size, sort 포함
     * ex) stores?page=1&size=10&sort=deliveryFees,asc&storeName=test1&categoryId=2
     */
    @GetMapping
    public List<ResponseStore> findAll(Authentication authentication,
                                       @ModelAttribute StoreSearchCondition searchCond,
                                       @PageableDefault(sort = "name", direction = ASC) Pageable pageable) {
        String regionCode = ((UserAuthentication) authentication).getRegionCode();
        searchCond.setRegionCode(regionCode);
        return storeService.findAll(searchCond, pageable).getContent();
    }

    @GetMapping("/{storeId}")
    public ResponseStore find(@PathVariable Long storeId) {
        return storeService.find(storeId);
    }
}
