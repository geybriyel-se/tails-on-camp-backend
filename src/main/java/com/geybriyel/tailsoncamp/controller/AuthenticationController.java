package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.ApiResponse;
import com.geybriyel.tailsoncamp.dto.LoginUserRequestDTO;
import com.geybriyel.tailsoncamp.dto.RegisterUserRequestDTO;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.exception.ObjectNotValidException;
import com.geybriyel.tailsoncamp.exception.UserRegistrationException;
import com.geybriyel.tailsoncamp.security.AuthenticationResponse;
import com.geybriyel.tailsoncamp.security.AuthenticationService;
import com.geybriyel.tailsoncamp.security.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final JwtService jwtService;

    @PostMapping("/register")
    public ApiResponse<AuthenticationResponse> register(@Valid @RequestBody RegisterUserRequestDTO request) {
        try {
            AuthenticationResponse token = authenticationService.register(request);
            return new ApiResponse<>(StatusCode.USER_CREATED, token);
        } catch (ObjectNotValidException exception) {
            return new ApiResponse<>(exception.getStatusCode(), exception.getViolations());
        } catch (UserRegistrationException exception) {
            StatusCode exceptionStatusCode = exception.getStatusCode();
            if (exceptionStatusCode == StatusCode.EMAIL_NOT_UNIQUE || exceptionStatusCode == StatusCode.USERNAME_NOT_UNIQUE) {
                return new ApiResponse<>(exceptionStatusCode, null);
            } else {
                log.error("An unexpected error occurred during user registration", exception);
                return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, null);
            }
        }
    }


    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@Valid @RequestBody LoginUserRequestDTO request) {
        try {
            AuthenticationResponse token = authenticationService.authenticate(request);
            String username = jwtService.extractUsername(token.getToken());
            Long userId = jwtService.extractUserId(token.getToken());
            String email = jwtService.extractEmail(token.getToken());
            log.info("Username: {} | UserId: {} | Email: {}", username, userId, email);

            return new ApiResponse<>(StatusCode.SUCCESS, token);
        } catch (UsernameNotFoundException exception) {
            return new ApiResponse<>(StatusCode.USER_DOES_NOT_EXIST, null);
        } catch (BadCredentialsException exception) {
            return new ApiResponse<>(StatusCode.INCORRECT_PASSWORD, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
