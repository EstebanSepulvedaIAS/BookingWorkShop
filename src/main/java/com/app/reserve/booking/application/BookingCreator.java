package com.app.reserve.booking.application;

import com.app.reserve.booking.domain.Booking;
import com.app.reserve.booking.domain.BookingRepository;
import com.app.reserve.booking.domain.exceptions.BookingException;
import com.app.reserve.booking.domain.useCases.CreateBooking;
import reactor.core.publisher.Mono;

public record BookingCreator(BookingRepository bookingRepository) implements CreateBooking {
    @Override
    public Mono<Void> createBooking(Booking booking) {
        String status = booking.getStatus();
        if(status.equalsIgnoreCase("CANCELLED")){
            return Mono.error(new BookingException("You cannot create a booking with a status cancelled"));
        }
        return bookingRepository.save(booking);
    }
}
