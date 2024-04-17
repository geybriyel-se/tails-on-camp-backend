package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class InvalidUserIdException extends RuntimeException {

    private final StatusCode statusCode = StatusCode.INVALID_USER_ID;

    public InvalidUserIdException() {
        super(StatusCode.INVALID_USER_ID.getMessage());
    }
}
