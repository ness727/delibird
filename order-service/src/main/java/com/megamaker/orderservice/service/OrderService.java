package com.megamaker.orderservice.service;

import com.megamaker.orderservice.dto.RequestOrder;
import com.megamaker.orderservice.dto.ResponseOrder;
import com.megamaker.orderservice.dto.product.ResponseProduct;
import com.megamaker.orderservice.exception.QuantityException;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void save(RequestOrder requestOrder);
    List<ResponseOrder> getOrderList(Long userId);

    default int getSum(Map<Long, Integer> productQuantityMap, List<ResponseProduct> productList) {
        int sum = 0;
        for (ResponseProduct product : productList) {
            int orderQty = productQuantityMap.get(product.getId());

            // 재고 보다 주문수량이 많을 때
            if (product.getQuantity() < orderQty) {
                throw new QuantityException("재고 부족");
            }

            sum += product.getPrice() * orderQty;
        }
        return sum;
    }
}
