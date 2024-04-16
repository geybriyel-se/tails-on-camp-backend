package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class InvalidPetIdException extends RuntimeException{

    private final StatusCode statusCode = StatusCode.INVALID_PET_ID;

    public InvalidPetIdException() {
        super(StatusCode.INVALID_PET_ID.getMessage());
    }
}
