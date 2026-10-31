package com.app.reserve.booking.domain.useCases;

import reactor.core.publisher.Mono;

public interface DeleteBooking {
    Mono<Void> deleteBookingById(String bookingId);
}
