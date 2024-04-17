package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class PetNotAvailableException extends RuntimeException {

    private final StatusCode statusCode = StatusCode.PET_NOT_AVAILABLE;

    public PetNotAvailableException() {
        super(StatusCode.PET_NOT_AVAILABLE.getMessage());
    }
}
