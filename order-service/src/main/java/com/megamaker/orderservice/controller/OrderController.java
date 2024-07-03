package com.megamaker.orderservice.controller;

import com.megamaker.orderservice.dto.ResponseOrder;
import com.megamaker.orderservice.exception.NoProductException;
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
import java.util.List;
import java.util.NoSuchElementException;

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

        String error = "";
        try {
            orderService.save(requestOrder);
            return ResponseEntity.created(URI.create(environment.getProperty("client.order_result"))).build();
        } catch (QuantityException e) {
            error = "재고 수량보다 적게 주문해주세요";
        } catch (NoProductException e) {
            error = "상품 조회에 실패했습니다";
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @GetMapping
    public ResponseEntity<?> getOrder(Authentication authentication) {
        Long userId = Long.valueOf(String.valueOf(authentication.getPrincipal()));

        try {
            List<ResponseOrder> result = orderService.getOrderList(userId);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
