package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class PetOnHoldException extends RuntimeException {

    private final StatusCode statusCode = StatusCode.PET_ON_HOLD;

    public PetOnHoldException() {
        super(StatusCode.PET_ON_HOLD.getMessage());
    }
}
