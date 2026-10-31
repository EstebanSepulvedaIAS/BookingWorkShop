package com.app.reserve.booking.infrastructure.api;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingResponse(UUID id, String status, LocalDateTime createdAt, LocalDateTime cancelledAt, UUID offerId, UUID userId) {
}
