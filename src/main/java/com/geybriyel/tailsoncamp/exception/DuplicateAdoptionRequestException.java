package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class DuplicateAdoptionRequestException extends RuntimeException {

    private final StatusCode statusCode = StatusCode.DUPLICATE_ADOPTION_REQUEST;

    public DuplicateAdoptionRequestException() {
        super(StatusCode.DUPLICATE_ADOPTION_REQUEST.getMessage());
    }
}
