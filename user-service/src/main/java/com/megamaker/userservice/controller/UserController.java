package com.megamaker.userservice.controller;

import com.megamaker.userservice.dto.*;
import com.megamaker.userservice.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

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

    // 가입 확인
    @PostMapping("/check")
    public ResponseEntity<ResponseCheckUser> isUserAlreadyRegistered(@RequestBody RequestCheckUser userProviderId) {
        try {
            ResponseCheckUser responseCheckUser = userService.isUserAlreadyRegistered(userProviderId.getProviderId());
            return ResponseEntity.ok(responseCheckUser);
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 특정 회원 조회
    @GetMapping
    public ResponseEntity<?> getUserByUserId(Authentication auth) {
        String userId = auth.getName();

        try {
            ResponseUser user = userService.getUser(userId);
            return ResponseEntity.ok(user);
        } catch (DataAccessException | NoSuchElementException e) {
            log.debug("User find err", e);
            return ResponseEntity.badRequest().body("회원 조회 실패");
        }
    }

    // 회원 정보 수정
    @PatchMapping
    public ResponseEntity<?> update(Authentication auth, RequestUpdateUser requestUpdateUser) {
        String userId = auth.getName();

        try {
            userService.update(userId, requestUpdateUser);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (NoSuchElementException | DataAccessException e) {
            return ResponseEntity.badRequest().body("입력값을 확인해 주세요");
        }
    }

//    private static String getJwtFromCookies(HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//        String jwt = null;
//
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("Auth")) {
//                jwt = cookie.getValue();
//                break;
//            }
//        }
//        if (jwt == null) throw new NoSuchElementException("쿠키 조회 실패");
//        return jwt;
//    }
}
