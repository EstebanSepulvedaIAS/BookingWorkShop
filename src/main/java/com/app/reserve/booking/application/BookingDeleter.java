package com.app.reserve.booking.application;

import com.app.reserve.booking.domain.BookingRepository;
import com.app.reserve.booking.domain.useCases.DeleteBooking;
import reactor.core.publisher.Mono;

public record BookingDeleter(BookingRepository bookingRepository) implements DeleteBooking {
    @Override
    public Mono<Void> deleteBookingById(String bookingId) {
        return bookingRepository.deleteBookingById(bookingId);
    }
}
