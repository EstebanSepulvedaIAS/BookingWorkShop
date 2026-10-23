package com.app.reserve.user.domain.valueObjects;

import com.app.reserve.shared.domain.exceptions.InvalidArgumentException;

public record UserEmail(String email) {
    private static final String emailRegexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+" +
            "(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public UserEmail{
        if(email == null || email.isBlank()){
            throw new InvalidArgumentException("Email doesnt blank");
        }
    }

    public void validateEmail(String email){
        if(!email.matches(emailRegexPattern)){
            throw new InvalidArgumentException(email + " is not a valid email address.");
        }
    }
}
