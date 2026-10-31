package com.app.reserve.booking.domain.useCases;

import com.app.reserve.booking.domain.Booking;
import reactor.core.publisher.Mono;

public interface CreateBooking {
    Mono<Void> createBooking(Booking booking);
}
