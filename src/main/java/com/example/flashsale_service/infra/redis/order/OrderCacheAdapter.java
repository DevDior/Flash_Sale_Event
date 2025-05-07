package com.example.flashsale_service.infra.redis.order;

import com.example.flashsale_service.domain.order.port.OrderCachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class OrderCacheAdapter implements OrderCachePort {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String PREFIX = "order::";

    @Override
    public void save(CachedOrder order) {
        String key = PREFIX + UUID.randomUUID();
        redisTemplate.opsForValue().set(key, ord
                .er);
    }

    @Override
    public List<String> getAllKeys() {
        Set<String> keys = redisTemplate.keys(PREFIX + "*");
        return keys == null ? Collections.emptyList() : new ArrayList<>(keys);
    }

    @Override
    public CachedOrder get(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value instanceof CachedOrder ? (CachedOrder) value : null;
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
