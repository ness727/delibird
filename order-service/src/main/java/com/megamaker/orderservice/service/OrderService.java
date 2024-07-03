package com.megamaker.orderservice.service;

import com.megamaker.orderservice.dto.RequestOrder;
import com.megamaker.orderservice.dto.ResponseOrder;

import java.util.List;

public interface OrderService {
    void save(RequestOrder requestOrder);
    List<ResponseOrder> getOrderList(Long userId);
}
