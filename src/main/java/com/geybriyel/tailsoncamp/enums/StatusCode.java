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
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED.value(), "The provided JWT has expired"),

    USERNAME_NOT_UNIQUE(HttpStatus.CONFLICT.value(), "Username is already taken"),
    INCORRECT_CREDENTIALS(HttpStatus.UNAUTHORIZED.value(), "Login failed. The provided username and/or password is incorrect"),
    EMAIL_NOT_UNIQUE(HttpStatus.CONFLICT.value(), "Email address is already in use"),
    USER_DOES_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "User does not exist"),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED.value(), "Incorrect password"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "Input values are not valid"),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST.value(), "Invalid user ID"),
    INVALID_USERNAME(HttpStatus.BAD_REQUEST.value(), "Invalid username"),

    LIST_EMPTY(HttpStatus.NOT_FOUND.value(), "The list is empty. No records found"),

    INVALID_PET_ID(HttpStatus.BAD_REQUEST.value(), "Invalid pet ID"),
    INVALID_BREED(HttpStatus.BAD_REQUEST.value(), "No pets of the specified breed was found."),
    DUPLICATE_PET(HttpStatus.CONFLICT.value(), "The pet already exists"),

    INVALID_SHELTER_ID(HttpStatus.BAD_REQUEST.value(), "Invalid shelter ID."),
    INVALID_CITY(HttpStatus.BAD_REQUEST.value(), "No shelters from the specified city was found in our database."),
    INVALID_PROVINCE(HttpStatus.BAD_REQUEST.value(), "No shelters from the specified province was found in our database."),
    INVALID_SHELTER_NAME(HttpStatus.BAD_REQUEST.value(), "Invalid shelter name."),
    DUPLICATE_SHELTER(HttpStatus.CONFLICT.value(), "The shelter already exists"),

    INVALID_ADOPTION_REQUEST_ID(HttpStatus.BAD_REQUEST.value(), "Invalid adoption request ID"),
    DUPLICATE_ADOPTION_REQUEST(HttpStatus.CONFLICT.value(), "Adoption request has already been created"),
    PET_NOT_AVAILABLE(HttpStatus.CONFLICT.value(), "Not available. Pet is already adopted."),
    PET_ON_HOLD(HttpStatus.CONFLICT.value(), "Request on hold. Request from another user is currently being finalized"),
    REJECTED_ADOPTION_REQUEST(HttpStatus.CONFLICT.value(), "Cannot proceed. Adoption request for pet was already rejected."),
    UNDERGOING_FINALIZATION(HttpStatus.CONFLICT.value(), "Cannot approve. Request from another user is already approved and undergoing finalization."),
    ADOPTION_REQUEST_NOT_APPROVED(HttpStatus.CONFLICT.value(), "Request must be approved first before proceeding."),

    SUCCESS(HttpStatus.OK.value(), "Success"),
    USER_CREATED(HttpStatus.CREATED.value(), "User created successfully"),
    ;


    private final int code;
    private final String message;
}
