package com.example.flashsale_service.domain.flashsale.entity;

import com.example.flashsale_service.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "flashsale_events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FlashsaleEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // 이벤트 시작 시간
    private LocalDateTime startTime;

    // 이벤트 종료 시간
    private LocalDateTime endTime;

    // 할인율
    private int discountPercentage;

    // 플래시 세일용 한정 수량
    private int totalQuantity;

    // 남은 수량
    private int remainingQuantity;

    // 이벤트 상태 (예: SCHEDULED, ONGING, ENDED)
    @Enumerated(EnumType.STRING)
    private FlashsaleStatus status;

    public void descreaseQuantity(int quantity) {
        if (this.status != FlashsaleStatus.ONGOING) {
            throw new IllegalStateException("진행 중인 이벤트가 아닙니다.");
        }

        if (this.remainingQuantity < quantity) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
        this.remainingQuantity -= quantity;
    }
}
