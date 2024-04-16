package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class InvalidProvinceException extends RuntimeException {

    private final StatusCode statusCode = StatusCode.INVALID_PROVINCE;

    public InvalidProvinceException() {
        super(StatusCode.INVALID_PROVINCE.getMessage());
    }
}
