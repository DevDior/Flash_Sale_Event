package com.example.flashsale_service.domain.order.service;

import com.example.flashsale_service.domain.flashsale.entity.FlashsaleEvent;
import com.example.flashsale_service.domain.flashsale.repository.FlashsaleEventRepository;
import com.example.flashsale_service.domain.order.entity.Order;
import com.example.flashsale_service.domain.order.entity.OrderStatus;
import com.example.flashsale_service.domain.order.port.OrderCachePort;
import com.example.flashsale_service.domain.order.repository.OrderRepository;
import com.example.flashsale_service.infra.redis.order.CachedOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class FlashsaleOrderService {

    private final FlashsaleEventRepository flashsaleEventRepository;
    private final OrderCachePort orderCachePort;

    private static final String SOLD_OUT_MSG = "Sold out!";

    @Transactional
    public void placeOrder(Long eventId, int quantity) {
        long beforeUpdate = System.currentTimeMillis();
        int updated = flashsaleEventRepository.decreaseQuantity(eventId, quantity);
        long updateDuration  = System.currentTimeMillis() - beforeUpdate;
        log.debug("[PERF] 재고 차감 쿼리 처리 시간: {}ms", updateDuration );

        if (updated == 0) {
            log.warn("{ORDER] 주문 실패 - 품절 상태 (eventId={})", eventId);
            throw new RuntimeException(SOLD_OUT_MSG);
        }

        CachedOrder cachedOrder = CachedOrder.builder()
                .eventId(eventId)
                .quantity(quantity)
                .orderTime(LocalDateTime.now())
                .build();

        long beforeCache = System.currentTimeMillis();
        orderCachePort.save(cachedOrder);
        long cacheDuration = System.currentTimeMillis() - beforeCache;
        log.debug("[PERF] 주문 Redis 캐시 처리 시간: {}ms", cacheDuration);

//        // 주문 생성
//        Order order = Order.builder()
//                .flashsaleEvent(FlashsaleEvent.builder().id(eventId).build())   // 조회 없이 ID만 설정
//                .quantity(quantity)
//                .orderTime(LocalDateTime.now())
//                .status(OrderStatus.CREATED)
//                .build();
//
//        long beforeInsert = System.currentTimeMillis();
//        orderRepository.save(order);
//        long insertDuration = System.currentTimeMillis() - beforeInsert;
//        log.debug("[PERF] 주문 저장 처리 시간: {}ms", insertDuration);
    }
}
