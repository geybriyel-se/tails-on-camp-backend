package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class ShelterIdNotFound extends RuntimeException{

    private final StatusCode statusCode = StatusCode.SHELTER_ID_NOT_FOUND;

    public ShelterIdNotFound() {
        super(StatusCode.SHELTER_ID_NOT_FOUND.getMessage());
    }
}
