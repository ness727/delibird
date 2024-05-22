package com.megamaker.cartservice.controller;

import com.megamaker.cartservice.dto.RequestAddCart;
import com.megamaker.cartservice.dto.ResponseCart;
import com.megamaker.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/cart")
@RestController
public class CartController {
    private final CartService cartService;

    @PostMapping
    public void add(Authentication authentication, @RequestBody RequestAddCart requestAddCart) {
        String userId = String.valueOf(authentication.getPrincipal());
        cartService.add(userId, requestAddCart);
    }

    @GetMapping
    public ResponseEntity<?> get(Authentication authentication) {
        try {
            String userId = String.valueOf(authentication.getPrincipal());
            return ResponseEntity.ok(cartService.get(userId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니 조회 실패");
        }
    }
}
