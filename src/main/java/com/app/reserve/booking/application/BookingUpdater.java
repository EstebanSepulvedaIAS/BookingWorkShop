package com.app.reserve.booking.application;

import com.app.reserve.booking.domain.BookingRepository;
import com.app.reserve.booking.domain.useCases.UpdateBooking;
import com.app.reserve.shared.domain.Id;
import reactor.core.publisher.Mono;

public record BookingUpdater(BookingRepository bookingRepository) implements UpdateBooking {
    @Override
    public Mono<Void> cancelledBookingId(Id id) {
        return bookingRepository.cancelledBookingById(id);
    }
}
