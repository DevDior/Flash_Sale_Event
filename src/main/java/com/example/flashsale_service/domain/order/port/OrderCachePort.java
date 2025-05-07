package com.example.flashsale_service.domain.order.port;

import com.example.flashsale_service.infra.redis.order.CachedOrder;

import java.util.List;

public interface OrderCachePort {

    void save(CachedOrder order);
    List<String> getAllKeys();
    CachedOrder get(String key);
    void remove(String key);
}
