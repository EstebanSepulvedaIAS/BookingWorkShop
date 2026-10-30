package com.app.reserve.booking.domain.valueObjects;

import com.app.reserve.shared.domain.exceptions.InvalidArgumentException;

public record BookingUserId(String value) {
    public BookingUserId{
        if(value == null || value.isBlank()){
            throw new InvalidArgumentException("User Id cannot be null or empty");
        }
    }
}
