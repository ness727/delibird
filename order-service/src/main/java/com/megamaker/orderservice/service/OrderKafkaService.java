package com.megamaker.orderservice.service;

import com.megamaker.orderservice.dto.OrderDto;
import com.megamaker.orderservice.dto.OrderProductDto;
import com.megamaker.orderservice.dto.ResponseOrder;
import com.megamaker.orderservice.exception.NoProductException;
import com.megamaker.orderservice.exception.QuantityException;
import com.megamaker.orderservice.dto.RequestOrder;
import com.megamaker.orderservice.dto.product.ResponseProduct;
import com.megamaker.orderservice.entity.Order;
import com.megamaker.orderservice.feignclient.StoreClient;
import com.megamaker.orderservice.kafka.OrderProducer;
import com.megamaker.orderservice.kafka.OrderProductProducer;
import com.megamaker.orderservice.repository.OrderRepository;
import com.megamaker.orderservice.util.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OrderKafkaService implements OrderService {
    private final OrderRepository orderRepository;
    private final StoreClient storeClient;
    private final OrderProducer orderProducer;
    private final OrderProductProducer orderProductProducer;

    @Override
    public void save(RequestOrder requestOrder) {
        Long orderId = saveOrderJpa(requestOrder);
        saveOrderProductKafka(orderId, requestOrder);
    }

    // JPA로 주문 저장
    private Long saveOrderJpa(RequestOrder requestOrder) {
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

        return orderRepository.save(order).getId();
    }

    // Kafka Connect로 주문 저장
//    public void saveOrderKafka(RequestOrder requestOrder) {
//        Map<Long, Integer> productQuantityMap = requestOrder.getProductQuantityMap();
//        OrderDto orderDto = OrderMapper.INSTANCE.toOrderDto(requestOrder);
//
//        // store-service 로 Product 검색
//        List<ResponseProduct> productList = getResponseProductList(productQuantityMap);
//
//        // 주문 금액 합계 계산
//        int sum = getSum(productQuantityMap, productList);
//        orderDto.setSumPrice(sum);
//
//        orderProducer.send(orderDto);
//    }

    private void saveOrderProductKafka(Long orderId, RequestOrder requestOrder) {
        Map<Long, Integer> productQuantityMap = requestOrder.getProductQuantityMap();
        List<OrderProductDto> orderProductDtoList = productQuantityMap.keySet().stream()
                .map(productId -> new OrderProductDto(orderId, productId, productQuantityMap.get(productId)))
                .toList();

        orderProductProducer.send(orderProductDtoList);
    }

    @Override
    public List<ResponseOrder> getOrderList(Long userId) {
        List<Order> foundOrder = orderRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
        return foundOrder.stream().map(OrderMapper.INSTANCE::toResponseOrder).toList();
    }

    // store-service로 상품 정보 조회 요청
    private List<ResponseProduct> getResponseProductList(Map<Long, Integer> productQuantityMap) {
        // store-service 로 Product 검색
        List<Long> productIdList = productQuantityMap.keySet().stream().toList();
        return storeClient.getProductList(productIdList);
    }
}
