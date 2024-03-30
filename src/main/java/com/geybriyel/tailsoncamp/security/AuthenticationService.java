package com.geybriyel.tailsoncamp.security;

import com.geybriyel.tailsoncamp.dto.LoginUserRequest;
import com.geybriyel.tailsoncamp.dto.RegisterUserRequest;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.enums.Role;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.exception.InvalidUserFieldsException;
import com.geybriyel.tailsoncamp.exception.UserRegistrationException;
import com.geybriyel.tailsoncamp.service.impl.UserDetailsServiceImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterUserRequest request) {
        if (userDetailsService.isUsernameTaken(request.getUsername())) {
            throw new UserRegistrationException(StatusCode.USERNAME_NOT_UNIQUE);
        }

        if (userDetailsService.isEmailTaken(request.getEmail())) {
            throw new UserRegistrationException(StatusCode.EMAIL_NOT_UNIQUE);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setRole(Role.USER);
        user.setPassword(request.getPassword());

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violationSet = validator.validate(user);
        List<String> violations = violationSet.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        if (!violations.isEmpty()) {
            throw new InvalidUserFieldsException(StatusCode.BAD_REQUEST, violations);
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userDetailsService.saveUser(user);

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(LoginUserRequest request) {
        User user = userDetailsService.loadUserByUsername(request.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
