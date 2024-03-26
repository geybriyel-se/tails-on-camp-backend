package com.geybriyel.tailsoncamp.security;

import com.geybriyel.tailsoncamp.dto.LoginUserRequest;
import com.geybriyel.tailsoncamp.dto.RegisterUserRequest;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.enums.Role;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.exception.UserRegistrationException;
import com.geybriyel.tailsoncamp.service.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        user = userDetailsService.saveUser(user);
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(LoginUserRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }
}
