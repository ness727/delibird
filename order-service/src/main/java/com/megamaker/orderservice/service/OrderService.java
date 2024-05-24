package com.megamaker.orderservice.service;

import com.megamaker.orderservice.exception.QuantityException;
import com.megamaker.orderservice.dto.RequestOrder;
import com.megamaker.orderservice.dto.product.ResponseProduct;
import com.megamaker.orderservice.entity.Order;
import com.megamaker.orderservice.feignclient.StoreClient;
import com.megamaker.orderservice.repository.OrderRepository;
import com.megamaker.orderservice.util.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final StoreClient storeClient;

    public void saveOrder(RequestOrder requestOrder) {
        Map<Long, Integer> productQuantityMap = requestOrder.getProductQuantityMap();
        Order order = OrderMapper.INSTANCE.toOrder(requestOrder);

        // store-service 로 Product 검색
        List<Long> productIdList = productQuantityMap.keySet().stream().toList();
        List<ResponseProduct> productList = storeClient.getProductList(productIdList);

        int sum = 0;
        for (ResponseProduct product : productList) {
            int orderQty = productQuantityMap.get(product.getId());

            // 재고 보다 주문수량이 많을 때
            if (product.getQuantity() < orderQty) {
                throw new QuantityException("재고 부족");
            }

            sum += product.getPrice() * orderQty;
        }
        order.setSumPrice(sum);
        orderRepository.save(order);
    }
}
