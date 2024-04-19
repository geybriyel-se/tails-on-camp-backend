package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class RequestNotApprovedException extends RuntimeException {

    private final StatusCode statusCode = StatusCode.ADOPTION_REQUEST_NOT_APPROVED;

    public RequestNotApprovedException() {
        super(StatusCode.ADOPTION_REQUEST_NOT_APPROVED.getMessage());
    }
}
