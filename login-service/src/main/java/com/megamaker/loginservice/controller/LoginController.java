package com.megamaker.loginservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final Environment environment;

    @GetMapping("/auth")
    public ResponseEntity<Void> sendToFront(HttpServletRequest request) {
        String token = request.getHeader("Auth");

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Auth", token)
                .location(URI.create(environment.getProperty("client.address")))
                .build();
    }

}
