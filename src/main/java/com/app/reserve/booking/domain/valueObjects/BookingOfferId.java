package com.app.reserve.booking.domain.valueObjects;

import com.app.reserve.shared.domain.exceptions.InvalidArgumentException;

public record BookingOfferId(String value) {
    public BookingOfferId{
        if(value == null || value.isBlank()){
            throw new InvalidArgumentException("Offer Id cannot be null or empty");
        }
    }
}
