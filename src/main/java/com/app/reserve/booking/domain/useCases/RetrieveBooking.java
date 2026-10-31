package com.app.reserve.booking.domain.useCases;

import com.app.reserve.booking.domain.Booking;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RetrieveBooking {
    Mono<Booking> getBooking(String bookingId);
    Flux<Booking> getAllBookings();
}
