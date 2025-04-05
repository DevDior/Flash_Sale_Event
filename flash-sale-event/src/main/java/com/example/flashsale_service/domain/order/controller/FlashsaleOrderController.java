package com.example.flashsale_service.domain.order.controller;

import com.example.flashsale_service.domain.order.service.FlashsaleOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/flashsale")
public class FlashsaleOrderController {

    private final FlashsaleOrderService orderService;

    @PostMapping("/{eventId}/order")
    public ResponseEntity<String> order(@PathVariable Long eventId) {
        try {
            orderService.placeOrder(eventId, 1);
            return ResponseEntity.ok("Order placed!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
