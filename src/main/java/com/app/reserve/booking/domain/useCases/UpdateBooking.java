package com.app.reserve.booking.domain.useCases;

import com.app.reserve.shared.domain.Id;
import reactor.core.publisher.Mono;

public interface UpdateBooking {
    Mono<Void> cancelledBookingId(Id id);
}
