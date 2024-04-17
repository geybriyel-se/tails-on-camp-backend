package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class InvalidAdoptionRequestIdException extends RuntimeException {

    private final StatusCode statusCode = StatusCode.INVALID_ADOPTION_REQUEST_ID;

    public InvalidAdoptionRequestIdException() {
        super(StatusCode.INVALID_PROVINCE.getMessage());
    }
}
