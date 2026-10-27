package com.app.reserve.offer.infrastructure.api;

import java.time.LocalDateTime;

public record OfferInput(String id, int quantity, LocalDateTime date, boolean state, String title) {
}
