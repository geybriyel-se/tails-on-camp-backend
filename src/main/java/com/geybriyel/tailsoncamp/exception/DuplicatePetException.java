package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class DuplicatePetException extends RuntimeException {

    private final StatusCode statusCode = StatusCode.DUPLICATE_SHELTER;

    public DuplicatePetException() {
        super(StatusCode.DUPLICATE_PET.getMessage());
    }
}
