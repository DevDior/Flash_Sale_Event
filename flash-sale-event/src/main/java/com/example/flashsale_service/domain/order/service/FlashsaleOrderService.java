package com.example.flashsale_service.domain.order.service;

import com.example.flashsale_service.domain.flashsale.entity.FlashsaleEvent;
import com.example.flashsale_service.domain.flashsale.repository.FlashsaleEventRepository;
import com.example.flashsale_service.domain.order.entity.Order;
import com.example.flashsale_service.domain.order.entity.OrderStatus;
import com.example.flashsale_service.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class FlashsaleOrderService {

    private final FlashsaleEventRepository flashsaleEventRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void placeOrder(Long eventId, int quantity) {
        int updated = flashsaleEventRepository.decreaseQuantity(eventId, quantity);
        if (updated == 0) {
            throw new RuntimeException("Sold out!");
        }

        // 주문 생성
        Order order = Order.builder()
                .flashsaleEvent(FlashsaleEvent.builder().id(eventId).build())   // 조회 없이 ID만 설정
                .quantity(quantity)
                .orderTime(LocalDateTime.now())
                .status(OrderStatus.CREATED)
                .build();

        orderRepository.save(order);
    }
}
