package com.geybriyel.tailsoncamp.exception;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import lombok.Getter;

@Getter
public class AnotherReqUndergoingFinalizationException extends RuntimeException {

    private StatusCode statusCode = StatusCode.UNDERGOING_FINALIZATION;

    public AnotherReqUndergoingFinalizationException() {
        super(StatusCode.UNDERGOING_FINALIZATION.getMessage());
    }
}
