package com.megamaker.cartservice.service;

import com.megamaker.cartservice.domain.Cart;
import com.megamaker.cartservice.dto.RequestAddCart;
import com.megamaker.cartservice.dto.ResponseCart;
import com.megamaker.cartservice.repository.RedisRepository;
import com.megamaker.cartservice.util.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CartService {
    private final RedisRepository redisRepository;

    public void add(String userId, RequestAddCart requestAddCart) {
        Cart cart = CartMapper.INSTANCE.toCart(requestAddCart);
        cart.setUserId(userId);
        redisRepository.save(cart);
    }

    public ResponseCart get(String userId) {
        Cart cart = redisRepository.findById(userId).orElseThrow();
        return CartMapper.INSTANCE.toResponseCart(cart);
    }
}
