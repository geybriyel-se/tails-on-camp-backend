package com.geybriyel.tailsoncamp.controller;

import com.geybriyel.tailsoncamp.dto.ApiResponse;
import com.geybriyel.tailsoncamp.dto.LoginUserRequestDTO;
import com.geybriyel.tailsoncamp.dto.RegisterUserRequestDTO;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.security.AuthenticationResponse;
import com.geybriyel.tailsoncamp.security.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ApiResponse<AuthenticationResponse> register(@Valid @RequestBody RegisterUserRequestDTO request) {
        AuthenticationResponse token = authenticationService.register(request);
        return new ApiResponse<>(StatusCode.USER_CREATED, token);
    }

    @Operation(summary = "Log in an existing user")
    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@Valid @RequestBody LoginUserRequestDTO request) {
        AuthenticationResponse token = authenticationService.authenticate(request);
        return new ApiResponse<>(StatusCode.SUCCESS, token);
    }

}
