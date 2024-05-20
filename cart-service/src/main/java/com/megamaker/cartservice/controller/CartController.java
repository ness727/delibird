package com.megamaker.cartservice.controller;

import com.megamaker.cartservice.dto.RequestAddCart;
import com.megamaker.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
