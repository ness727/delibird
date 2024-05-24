package com.megamaker.loginservice.controller;

import com.megamaker.loginservice.security.oauth2.CustomOAuth2User;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

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

    @GetMapping("/login/dev")
    public void devLogin(HttpServletResponse response) throws IOException {
        String userId = "4ab483b0-c6b0-4584-8b50-9dd22ce2c558";
        Key secretKey = new SecretKeySpec(Base64.getEncoder().encode(environment.getProperty("token.secret").getBytes()),
                Jwts.SIG.HS256.key().build().getAlgorithm());

        String token = Jwts.builder()
                .subject(userId)
                .expiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(secretKey)
                .compact();

        response.setHeader("Auth", token);
    }
}
