package com.geybriyel.tailsoncamp.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geybriyel.tailsoncamp.dto.ApiResponse;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import kong.unirest.UnirestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleGenericException(Exception e) {
        return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ApiResponse<Object> handleUserRegistrationException(UserRegistrationException e) {
        StatusCode exceptionStatusCode = e.getStatusCode();
        if (exceptionStatusCode == StatusCode.EMAIL_NOT_UNIQUE || exceptionStatusCode == StatusCode.USERNAME_NOT_UNIQUE) {
            return new ApiResponse<>(exceptionStatusCode, null);
        } else {
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ExceptionHandler(InvalidUserIdException.class)
    public ApiResponse<Object> handleInvalidUserIdException(InvalidUserIdException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ApiResponse<Object> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ApiResponse<>(StatusCode.USER_DOES_NOT_EXIST, null);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ApiResponse<Object> handleUsernameNotFoundException(BadCredentialsException e) {
        return new ApiResponse<>(StatusCode.INCORRECT_PASSWORD, null);
    }

    @ExceptionHandler(InvalidShelterIdException.class)
    public ApiResponse<Object> handleInvalidShelterIdException(InvalidShelterIdException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(InvalidCityException.class)
    public ApiResponse<Object> handleInvalidCityException(InvalidCityException e) {
        return new ApiResponse<>(e.getStatusCode(), e.getMessage());
    }

    @ExceptionHandler(InvalidProvinceException.class)
    public ApiResponse<Object> handleInvalidProvinceException(InvalidProvinceException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(InvalidShelterNameException.class)
    public ApiResponse<Object> handleInvalidShelterNameException(InvalidShelterNameException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(ObjectNotValidException.class)
    public ApiResponse<Object> handleObjectNotValidException(ObjectNotValidException e) {
        return new ApiResponse<>(e.getStatusCode(), e.getViolations());
    }

    @ExceptionHandler(DuplicateShelterException.class)
    public ApiResponse<Object> handleDuplicateShelterException(DuplicateShelterException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(InvalidPetIdException.class)
    public ApiResponse<Object> handleInvalidPetIdException(InvalidPetIdException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(InvalidBreedException.class)
    public ApiResponse<Object> handleInvalidBreedException(InvalidBreedException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(DuplicatePetException.class)
    public ApiResponse<Object> handleDuplicatePetException(DuplicatePetException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(InvalidAdoptionRequestIdException.class)
    public ApiResponse<Object> handleInvalidAdoptionRequestIdException(InvalidAdoptionRequestIdException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(PetNotAvailableException.class)
    public ApiResponse<Object> handlePetNotAvailableException(PetNotAvailableException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(PetOnHoldException.class)
    public ApiResponse<Object> handlePetOnHoldException(PetOnHoldException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(DuplicateAdoptionRequestException.class)
    public ApiResponse<Object> handleDuplicateAdoptionRequestException(DuplicateAdoptionRequestException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(AnotherReqUndergoingFinalizationException.class)
    public ApiResponse<Object> handleAnotherReqUndergoingFinalizationException(AnotherReqUndergoingFinalizationException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(RequestNotApprovedException.class)
    public ApiResponse<Object> handleRequestNotApprovedException(RequestNotApprovedException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(RejectedAdoptionRequestException.class)
    public ApiResponse<Object> handleRejectedAdoptionRequestException(RejectedAdoptionRequestException e) {
        return new ApiResponse<>(e.getStatusCode(), null);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ApiResponse<Object> handleJsonProcessingException(JsonProcessingException e) {
        return new ApiResponse<>(StatusCode.JSON_PARSE_ERROR, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ApiResponse<>(StatusCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ApiResponse<Object> handleExpiredJwtException(ExpiredJwtException e) {
        return new ApiResponse<>(StatusCode.EXPIRED_JWT, null);
    }



}
