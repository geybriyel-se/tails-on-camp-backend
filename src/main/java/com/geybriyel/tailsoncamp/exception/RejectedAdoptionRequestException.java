package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class RejectedAdoptionRequestException extends RuntimeException {

    private final StatusCode statusCode = StatusCode.REJECTED_ADOPTION_REQUEST;

    public RejectedAdoptionRequestException() {
        super(StatusCode.REJECTED_ADOPTION_REQUEST.getMessage());
    }
}
