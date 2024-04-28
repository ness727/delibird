package com.megamaker.userservice.controller;

import com.megamaker.userservice.dto.RequestRegisterUser;
import com.megamaker.userservice.dto.ResponseRegisterUser;
import com.megamaker.userservice.dto.ResponseUser;
import com.megamaker.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ResponseRegisterUser> register(@RequestBody RequestRegisterUser user) {
        try {
            ResponseRegisterUser responseRegisterUser = userService.register(user);
            return ResponseEntity.ok().body(responseRegisterUser);
        } catch (DataAccessException e) {
            log.error("Save err", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable String userId) {
        try {
            ResponseUser user = userService.getUser(userId);
            return ResponseEntity.ok().body(user);
        } catch (DataAccessException e) {
            log.error("User find err", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/user/{providerId}")
    public ResponseEntity<String> isUserAlreadyRegistered(@PathVariable String providerId) {
        if (userService.isUserAlreadyRegistered(providerId)) return ResponseEntity.ok().build();
        else return ResponseEntity.notFound().build();
    }
}
