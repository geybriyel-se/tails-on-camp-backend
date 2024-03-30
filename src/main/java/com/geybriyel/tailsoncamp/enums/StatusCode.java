package com.geybriyel.tailsoncamp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum StatusCode {

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Resource not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error. An error occurred while retrieving the data"),
    JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST.value(), "Error parsing the JSON object"),
    USERNAME_NOT_UNIQUE(HttpStatus.CONFLICT.value(), "Username is already taken"),
    INCORRECT_CREDENTIALS(HttpStatus.UNAUTHORIZED.value(), "Login failed. The provided username and/or password is incorrect"),
    EMAIL_NOT_UNIQUE(HttpStatus.CONFLICT.value(), "Email address is already in use"),
    USER_DOES_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "User does not exist"),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED.value(), "Incorrect password"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "Input values are not valid"),

    SUCCESS(HttpStatus.OK.value(), "Success"),
    USER_CREATED(HttpStatus.CREATED.value(), "User created successfully"),
    ;


    private final int code;
    private final String message;
}
