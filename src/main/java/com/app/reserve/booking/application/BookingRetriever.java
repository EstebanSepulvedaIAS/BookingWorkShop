package com.app.reserve.booking.application;

import com.app.reserve.booking.domain.Booking;
import com.app.reserve.booking.domain.BookingRepository;
import com.app.reserve.booking.domain.useCases.RetrieveBooking;
import com.app.reserve.shared.domain.Id;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public record BookingRetriever(BookingRepository bookingRepository) implements RetrieveBooking {
    @Override
    public Mono<Booking> getBooking(String bookingId) {
        return bookingRepository.findById(new Id(bookingId));
    }

    @Override
    public Flux<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
