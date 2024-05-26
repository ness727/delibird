package com.megamaker.orderservice.service;

import com.megamaker.orderservice.dto.kafka.OrderDto;
import com.megamaker.orderservice.entity.OrderProduct;
import com.megamaker.orderservice.exception.QuantityException;
import com.megamaker.orderservice.dto.RequestOrder;
import com.megamaker.orderservice.dto.product.ResponseProduct;
import com.megamaker.orderservice.entity.Order;
import com.megamaker.orderservice.feignclient.StoreClient;
import com.megamaker.orderservice.kafka.OrderProducer;
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
    private final OrderProducer orderProducer;


    // JPA로 주문 저장
    public void saveOrder(RequestOrder requestOrder) {
        Map<Long, Integer> productQuantityMap = requestOrder.getProductQuantityMap();
        Order order = OrderMapper.INSTANCE.toOrder(requestOrder);

        // store-service 로 Product 검색
        List<ResponseProduct> productList = getResponseProductList(productQuantityMap);

        // 주문 금액 합계 계산
        int sum = getSum(productQuantityMap, productList);
        order.setSumPrice(sum);

        orderRepository.save(order);
    }

    // Kafka Connect로 주문 저장
    public void saveOrderKafka(RequestOrder requestOrder) {
        Map<Long, Integer> productQuantityMap = requestOrder.getProductQuantityMap();
        OrderDto orderDto = OrderMapper.INSTANCE.toOrderDto(requestOrder);

        // store-service 로 Product 검색
        List<ResponseProduct> productList = getResponseProductList(productQuantityMap);

        // 주문 금액 합계 계산
        int sum = getSum(productQuantityMap, productList);
        orderDto.setSumPrice(sum);

        orderProducer.send("orders", orderDto);
    }

    private static int getSum(Map<Long, Integer> productQuantityMap, List<ResponseProduct> productList) {
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

    private List<ResponseProduct> getResponseProductList(Map<Long, Integer> productQuantityMap) {
        // store-service 로 Product 검색
        List<Long> productIdList = productQuantityMap.keySet().stream().toList();
        return storeClient.getProductList(productIdList);
    }
}
