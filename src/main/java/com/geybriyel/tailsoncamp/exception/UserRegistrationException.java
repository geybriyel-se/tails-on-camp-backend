package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class UserRegistrationException extends AuthenticationException {

    private final StatusCode statusCode;

    public UserRegistrationException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }

}
