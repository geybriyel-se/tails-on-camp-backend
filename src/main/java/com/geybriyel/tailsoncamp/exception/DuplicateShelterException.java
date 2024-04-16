package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class DuplicateShelterException extends RuntimeException {

    private final StatusCode statusCode = StatusCode.DUPLICATE_SHELTER;

    public DuplicateShelterException() {
        super(StatusCode.DUPLICATE_SHELTER.getMessage());
    }
}
