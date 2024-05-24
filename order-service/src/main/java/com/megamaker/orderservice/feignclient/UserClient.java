package com.megamaker.orderservice.feignclient;

import com.megamaker.orderservice.dto.user.ResponseUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("/users")
    ResponseUser getUserByToken(@RequestHeader("Auth") String token);
}