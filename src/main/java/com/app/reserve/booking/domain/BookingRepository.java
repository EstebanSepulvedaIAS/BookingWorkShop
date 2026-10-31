package com.app.reserve.booking.domain;

import com.app.reserve.shared.domain.Id;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookingRepository {
    Mono<Void> save(Booking booking);
    Mono<Booking> findById(Id id);
    Flux<Booking> findAll();
    Mono<Void> cancelledBookingById(Id id);
    Mono<Void> deleteBookingById(String id);
}
