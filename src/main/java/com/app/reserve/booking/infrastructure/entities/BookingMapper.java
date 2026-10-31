package com.app.reserve.booking.infrastructure.entities;

import com.app.reserve.booking.domain.Booking;

public class BookingMapper {

    private BookingMapper(){}

    public static BookingEntity toBookingEntity(Booking booking){
        return new BookingEntity(
                booking.getId(),
                booking.getStatus(),
                booking.getCreatedAt(),
                booking.getCancelledAt(),
                booking.getOfferId(),
                booking.getUserId()
        );
    }

    public static Booking toDomainBooking(BookingEntity bookingEntity){
        return Booking.create(
                bookingEntity.getId().toString(),
                bookingEntity.getStatus(),
                bookingEntity.getCreatedAt(),
                bookingEntity.getCancelledAt(),
                bookingEntity.getOfferId().toString(),
                bookingEntity.getUserId().toString()
        );
    }
}
