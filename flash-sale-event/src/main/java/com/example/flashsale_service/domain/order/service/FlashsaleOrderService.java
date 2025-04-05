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
        FlashsaleEvent event = flashsaleEventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // 객체 내부 메서드로 재고 감소
        event.descreaseQuantity(quantity);

        Order order = Order.builder()
                .flashsaleEvent(event)
                .quantity(quantity)
                .orderTime(LocalDateTime.now())
                .status(OrderStatus.CREATED)
                .build();

        orderRepository.save(order);
    }
}
