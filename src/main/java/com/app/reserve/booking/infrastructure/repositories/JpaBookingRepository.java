package com.app.reserve.booking.infrastructure.repositories;

import com.app.reserve.booking.infrastructure.entities.BookingEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface JpaBookingRepository extends ReactiveCrudRepository<BookingEntity, UUID> {
    @Modifying
    @Query("UPDATE booking SET status = 'CANCELLED', cancelled_at = CURRENT_TIMESTAMP WHERE id = :id AND status = 'BOOKED'")
    Mono<Integer> cancelledBookingById(@Param("id") UUID id);

    Mono<Boolean> existsByUserIdAndStatus(UUID userId, String status);
}
