package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class InvalidShelterNameException extends RuntimeException {

    private final StatusCode statusCode = StatusCode.INVALID_SHELTER_NAME;

    public InvalidShelterNameException() {
        super(StatusCode.INVALID_SHELTER_NAME.getMessage());
    }
}
