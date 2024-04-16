package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class InvalidCityException extends RuntimeException {

    private final StatusCode statusCode = StatusCode.INVALID_CITY;

    public InvalidCityException() {
        super(StatusCode.INVALID_CITY.getMessage());
    }
}
