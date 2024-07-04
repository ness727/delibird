package com.megamaker.orderservice.service;

import com.megamaker.orderservice.dto.OrderProductDto;
import com.megamaker.orderservice.dto.RequestOrder;
import com.megamaker.orderservice.dto.ResponseOrder;
import com.megamaker.orderservice.dto.product.ResponseProduct;
import com.megamaker.orderservice.entity.Order;
import com.megamaker.orderservice.entity.OrderProduct;
import com.megamaker.orderservice.exception.NoProductException;
import com.megamaker.orderservice.exception.QuantityException;
import com.megamaker.orderservice.feignclient.StoreClient;
import com.megamaker.orderservice.kafka.OrderProducer;
import com.megamaker.orderservice.kafka.OrderProductProducer;
import com.megamaker.orderservice.repository.OrderProductRepository;
import com.megamaker.orderservice.repository.OrderRepository;
import com.megamaker.orderservice.util.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

//@Service
@Transactional
@RequiredArgsConstructor
public class OrderJpaService implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final StoreClient storeClient;

    @Override
    public void save(RequestOrder requestOrder) {
        Order order = saveOrderJpa(requestOrder);
        saveOrderProductJpa(order, requestOrder);
    }

    private Order saveOrderJpa(RequestOrder requestOrder) {
        Map<Long, Integer> productQuantityMap = requestOrder.getProductQuantityMap();
        Order order = OrderMapper.INSTANCE.toOrder(requestOrder);

        // store-service 로 Product 검색
        List<ResponseProduct> productList = getResponseProductList(productQuantityMap);

        // 상품이 없을 때
        if (productList == null || productList.size() == 0) throw new NoProductException("상품 조회 실패");

        // 재고가 충분한지 확인
        for (ResponseProduct dbProduct : productList) {
            if (dbProduct.getQuantity() < productQuantityMap.get(dbProduct.getId()))
                throw new QuantityException("재고 부족");
        }

        // 주문 금액 합계 계산
        int sum = getSum(productQuantityMap, productList);
        order.setSumPrice(sum);

        return orderRepository.save(order);
    }

    private void saveOrderProductJpa(Order order, RequestOrder requestOrder) {
        Map<Long, Integer> productQuantityMap = requestOrder.getProductQuantityMap();
        
        // 주문 받은대로 저장
        List<OrderProduct> OrderProductList = productQuantityMap.entrySet().stream()
                .map(ks ->
                        OrderProduct.builder()
                                .order(order)
                                .productId(ks.getKey())
                                .quantity(ks.getValue())
                                .build()
                )
                .toList();
        
        orderProductRepository.saveAll(OrderProductList);  // DB 저장
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        storeClient.updateQuantity(productQuantityMap, String.valueOf(authentication.getCredentials()));  // 수량 갱신
    }

    @Override
    public List<ResponseOrder> getOrderList(Long userId) {
        return null;
    }

    // store-service로 상품 정보 조회 요청
    private List<ResponseProduct> getResponseProductList(Map<Long, Integer> productQuantityMap) {
        // store-service 로 Product 검색
        List<Long> productIdList = productQuantityMap.keySet().stream().toList();
        return storeClient.getProductList(productIdList);
    }
}
