package com.example.flashsale_service.domain.order.entity;

import com.example.flashsale_service.domain.flashsale.entity.FlashsaleEvent;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashsale_event_id")
    private FlashsaleEvent flashsaleEvent;

    // 주문한 수량 (플래시 세일의 경우 보통 1개로 제한)
    private int quantity;

    // 주문 생성 시간
    private LocalDateTime orderTime;

    // 주문 상태 (예: CREATED, PAID, CANCELLED)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
