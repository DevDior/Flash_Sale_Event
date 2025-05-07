package com.example.flashsale_service.infra.redis.order;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CachedOrder implements Serializable {

    private Long eventId;
    private int quantity;
    private LocalDateTime orderTime;
}
