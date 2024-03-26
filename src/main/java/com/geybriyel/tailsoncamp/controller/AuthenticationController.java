package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.ApiResponse;
import com.geybriyel.tailsoncamp.dto.LoginUserRequest;
import com.geybriyel.tailsoncamp.dto.RegisterUserRequest;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.exception.UserRegistrationException;
import com.geybriyel.tailsoncamp.security.AuthenticationResponse;
import com.geybriyel.tailsoncamp.security.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApiResponse<AuthenticationResponse> register(@RequestBody RegisterUserRequest request) {
        try {
            AuthenticationResponse token = authenticationService.register(request);
            return new ApiResponse<>(StatusCode.USER_CREATED, token);
        } catch (UserRegistrationException exception) {
            StatusCode exceptionStatusCode = exception.getStatusCode();
            if (exceptionStatusCode == StatusCode.EMAIL_NOT_UNIQUE) {
                return new ApiResponse<>(StatusCode.EMAIL_NOT_UNIQUE, null);
            } else if (exceptionStatusCode == StatusCode.USERNAME_NOT_UNIQUE) {
                return new ApiResponse<>(StatusCode.USERNAME_NOT_UNIQUE, null);
            } else {
                log.error("An unexpected error occurred during user registration", exception);
                return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, null);
            }
        }
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody LoginUserRequest request) {
        try {
            AuthenticationResponse token = authenticationService.authenticate(request);
            return new ApiResponse<>(StatusCode.SUCCESS, token);
        } catch (AuthenticationException exception) {
            return new ApiResponse<>(StatusCode.INCORRECT_CREDENTIALS, null);
        }
    }

}
