package com.example.flashsale_service.domain.flashsale.repository;

import com.example.flashsale_service.domain.flashsale.entity.FlashsaleEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashsaleEventRepository extends JpaRepository<FlashsaleEvent, Long> {
}
