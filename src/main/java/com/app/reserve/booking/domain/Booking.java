package com.app.reserve.booking.domain;

import com.app.reserve.booking.domain.valueObjects.*;
import com.app.reserve.shared.domain.Id;

import java.time.LocalDateTime;

public class Booking {

    private final Id id;
    private final BookingStatus status;
    private final BookingCreatedAt createdAt;
    private final BookingCancelledAt cancelledAt;
    private final BookingOfferId offerId;
    private final BookingUserId userId;

    public Booking(String id, String status, LocalDateTime createdAt, LocalDateTime cancelledAt, String offerId, String userId) {
        this.id = new Id(id);
        this.status = new BookingStatus(status);
        this.createdAt = new BookingCreatedAt(createdAt);
        this.cancelledAt = new BookingCancelledAt(cancelledAt);
        this.offerId = new BookingOfferId(offerId);
        this.userId = new BookingUserId(userId);
    }

    public static Booking create(String id, String status, LocalDateTime createdAt, LocalDateTime cancelledAt, String offerId, String userId){
        return new Booking(id, status, createdAt, cancelledAt, offerId, userId);
    }
}
