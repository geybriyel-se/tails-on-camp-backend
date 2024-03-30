package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class InvalidUserFieldsException extends RuntimeException {
    private final StatusCode statusCode;
    private final Object violations;

    public InvalidUserFieldsException(StatusCode statusCode, Object violations) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
        this.violations = violations;
    }

}
