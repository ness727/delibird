package com.megamaker.loginservice.feignclient;


import com.megamaker.loginservice.dto.RequestCheckUser;
import com.megamaker.loginservice.dto.RequestRegisterUser;
import com.megamaker.loginservice.dto.ResponseCheckUser;
import com.megamaker.loginservice.dto.ResponseRegisterUser;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("user-service")
public interface UserClient {
    //@Headers("Content-Type: application/json")
    @PostMapping("/users/check")
    ResponseCheckUser isUserAlreadyRegistered(RequestCheckUser requestCheckUser);

    @PostMapping("/users")
    ResponseRegisterUser register(RequestRegisterUser requestRegisterUser);
}