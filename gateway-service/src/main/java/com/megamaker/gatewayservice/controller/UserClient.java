package com.megamaker.gatewayservice.controller;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("stores")
public interface StoreClient {
    @RequestMapping(method = RequestMethod.GET, value = "/stores")
    List<Store> getStores();

    @GetMapping("/stores")
    Page<Store> getStores(Pageable pageable);

    @PostMapping(value = "/stores/{storeId}", consumes = "application/json")
    Store update(@PathVariable("storeId") Long storeId, Store store);

    @DeleteMapping("/stores/{storeId:\\d+}")
    void delete(@PathVariable Long storeId);
}