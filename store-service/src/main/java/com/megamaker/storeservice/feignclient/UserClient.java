package com.megamaker.storeservice.feignclient;

import com.megamaker.storeservice.dto.user.ResponseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("/users")
    ResponseUser getUserByToken(@RequestHeader("Auth") String token);
}