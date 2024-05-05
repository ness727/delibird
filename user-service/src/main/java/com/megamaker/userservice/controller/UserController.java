package com.megamaker.userservice.controller;

import com.megamaker.userservice.dto.*;
import com.megamaker.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    // 회원 가입
    @PostMapping
    public ResponseEntity<ResponseRegisterUser> register(@RequestBody RequestRegisterUser user) {
        try {
            ResponseRegisterUser responseRegisterUser = userService.register(user);
            return ResponseEntity.ok(responseRegisterUser);
        } catch (DataAccessException e) {
            log.error("Save err", e);
            return ResponseEntity.badRequest().build();
        }
    }

    // 특정 회원 조회
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseUser> getUserByUserId(@PathVariable String userId) {
        try {
            ResponseUser user = userService.getUser(userId);
            return ResponseEntity.ok(user);
        } catch (DataAccessException e) {
            log.error("User find err", e);
            return ResponseEntity.notFound().build();
        }
    }

    // 가입 확인
    @PostMapping("/check")
    public ResponseEntity<ResponseCheckUser> isUserAlreadyRegistered(@RequestBody RequestCheckUser userProviderId) {
        log.info("check");
        try {
            ResponseCheckUser responseCheckUser = userService.isUserAlreadyRegistered(userProviderId.getProviderId());
            return ResponseEntity.ok(responseCheckUser);
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
