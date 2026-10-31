package com.app.reserve.booking.infrastructure.api;

import java.time.LocalDateTime;

public record BookingInput(String id, String status, LocalDateTime createdAt, LocalDateTime cancelledAt, String offerId, String userId) {
}
