package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class PetNotFoundException extends RuntimeException{

    private final StatusCode statusCode = StatusCode.PET_NOT_FOUND;

    public PetNotFoundException() {
        super(StatusCode.PET_NOT_FOUND.getMessage());
    }
}
