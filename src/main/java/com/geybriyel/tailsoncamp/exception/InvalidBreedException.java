package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class InvalidBreedException extends RuntimeException{

    private final StatusCode statusCode = StatusCode.INVALID_BREED;

    public InvalidBreedException() {
        super(StatusCode.INVALID_BREED.getMessage());
    }
}
