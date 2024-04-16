package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class InvalidShelterIdException extends RuntimeException{

    private final StatusCode statusCode = StatusCode.INVALID_SHELTER_ID;

    public InvalidShelterIdException() {
        super(StatusCode.INVALID_SHELTER_ID.getMessage());
    }
}
