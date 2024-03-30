package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class PasswordIncorrectException extends AuthenticationException {

    private final StatusCode statusCode = StatusCode.INCORRECT_PASSWORD;

    public PasswordIncorrectException() {
        super(StatusCode.INCORRECT_PASSWORD.getMessage());
    }

}
