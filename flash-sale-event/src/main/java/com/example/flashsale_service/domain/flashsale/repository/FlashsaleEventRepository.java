package com.example.flashsale_service.domain.flashsale.repository;

import com.example.flashsale_service.domain.flashsale.entity.FlashsaleEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlashsaleEventRepository extends JpaRepository<FlashsaleEvent, Long> {

    @Modifying
    @Query("""
            UPDATE FlashsaleEvent f
            SET f.remainingQuantity = f.remainingQuantity - :qty
            WHERE f.id = :id AND f.remainingQuantity >= :qty
            """)
    int decreaseQuantity(@Param("id") Long id, @Param("qty") int qty);
}
