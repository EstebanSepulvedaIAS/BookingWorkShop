package com.app.reserve.booking.domain;

import com.app.reserve.booking.domain.valueObjects.*;
import com.app.reserve.shared.domain.Id;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public UUID getId(){return id.value();}
    public String getStatus(){return status.status();}
    public LocalDateTime getCreatedAt(){return createdAt.createdAt();}
    public LocalDateTime getCancelledAt(){return cancelledAt.cancelledAt();}
    public UUID getOfferId(){return UUID.fromString(offerId.value());}
    public UUID getUserId(){return UUID.fromString(userId.value());}

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", cancelledAt=" + cancelledAt +
                ", offerId=" + offerId +
                ", userId=" + userId +
                '}';
    }
}
