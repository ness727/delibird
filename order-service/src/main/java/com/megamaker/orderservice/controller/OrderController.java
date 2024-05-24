package com.megamaker.orderservice.controller;

import com.megamaker.orderservice.exception.QuantityException;
import com.megamaker.orderservice.dto.RequestOrder;
import com.megamaker.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrderController {
    private final Environment environment;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> saveOrder(Authentication authentication, @RequestBody RequestOrder requestOrder) {
        Long userId = Long.valueOf(String.valueOf(authentication.getPrincipal()));
        requestOrder.setUserId(userId);

        try {
            orderService.saveOrder(requestOrder);
            return ResponseEntity.created(URI.create(environment.getProperty("client.order_result"))).build();
        } catch (QuantityException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("상품 재고를 확인해 주세요");
        }
    }
}
