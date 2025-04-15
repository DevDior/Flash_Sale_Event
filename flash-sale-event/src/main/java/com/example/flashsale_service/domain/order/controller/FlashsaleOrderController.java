package com.example.flashsale_service.domain.order.controller;

import com.example.flashsale_service.domain.order.service.FlashsaleOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/flashsale")
public class FlashsaleOrderController {

    private final FlashsaleOrderService orderService;

    @PostMapping("/{eventId}/order")
    public ResponseEntity<String> order(@PathVariable Long eventId) {
        long start = System.currentTimeMillis(); // 전체 요청 시작 시간

        try {
            orderService.placeOrder(eventId, 1);
            return ResponseEntity.ok("Order placed!");
        } catch (RuntimeException e) {
            if (!"Sold out!".equals(e.getMessage())) {
                log.error("[ORDER] 알 수 없는 에러 발생 - eventId={}", eventId, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
            }

            // Sold out!인 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("[PERF] 주문 전체 처리 시간: {}ms", duration);
        }
    }
}
