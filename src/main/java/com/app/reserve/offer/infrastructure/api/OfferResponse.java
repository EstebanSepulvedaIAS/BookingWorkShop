package com.app.reserve.offer.infrastructure.api;

import java.time.LocalDateTime;
import java.util.UUID;

public record OfferResponse(UUID id, int quantity, LocalDateTime date, boolean state, String title) {
}
