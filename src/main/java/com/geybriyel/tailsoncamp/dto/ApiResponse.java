package com.geybriyel.tailsoncamp.dto;

import com.geybriyel.tailsoncamp.enums.StatusCode;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashMap;

public class ApiResponse<T> extends LinkedHashMap<String, Object> {

    public ApiResponse(StatusCode statusCode, Object body) {
        this.put("timestamp", Instant.now().toString());
        this.put("status", statusCode.getCode());
        this.put("message", statusCode.getMessage());
        this.put("body", body);
    }

    public int getStatus() {
        return (int) this.get("status");
    }

    public String getMessage() {
        return (String) this.get("message");
    }

    public T getBody() {
        return (T) this.get("body");
    }
}

