package com.example.flashsale_service.infra.redis.order;

import com.example.flashsale_service.domain.flashsale.entity.FlashsaleEvent;
import com.example.flashsale_service.domain.order.entity.Order;
import com.example.flashsale_service.domain.order.entity.OrderStatus;
import com.example.flashsale_service.domain.order.port.OrderCachePort;
import com.example.flashsale_service.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisOrderFlusher {

    private final OrderCachePort orderCachePort;
    private final OrderRepository orderRepository;

    @Scheduled(fixedRate = 5000)
    public void flushCachedOrders() {
        List<String> keys = orderCachePort.getAllKeys();
        if (keys.isEmpty()) return;

        List<Order> orders = keys.stream()
                .map(key -> {
                    CachedOrder cached = orderCachePort.get(key);
                    orderCachePort.remove(key);

                    return Order.builder()
                            .flashsaleEvent(FlashsaleEvent.builder().id(cached.getEventId()).build())
                            .quantity(cached.getQuantity())
                            .orderTime(cached.getOrderTime())
                            .status(OrderStatus.CREATED)
                            .build();
                })
                .collect(Collectors.toList());

        orderRepository.saveAll(orders);
        log.info("[Flush] {} orders flushed from Redis to DB", orders.size());
    }
}
