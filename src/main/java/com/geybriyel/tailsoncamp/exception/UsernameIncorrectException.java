package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class UsernameIncorrectException extends AuthenticationException {

    private final StatusCode statusCode = StatusCode.USER_DOES_NOT_EXIST;

    public UsernameIncorrectException() {
        super(StatusCode.USER_DOES_NOT_EXIST.getMessage());
    }

}
