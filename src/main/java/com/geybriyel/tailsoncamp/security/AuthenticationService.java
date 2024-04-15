package com.geybriyel.tailsoncamp.security;

import com.geybriyel.tailsoncamp.dto.LoginUserRequestDTO;
import com.geybriyel.tailsoncamp.dto.RegisterUserRequestDTO;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.enums.Role;
import com.geybriyel.tailsoncamp.service.impl.UserDetailsServiceImpl;
import com.geybriyel.tailsoncamp.validator.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final ObjectsValidator<User> userValidator;


    public AuthenticationResponse register(RegisterUserRequestDTO request) {
        /*if (userDetailsService.isUsernameTaken(request.getUsername())) {
            throw new UserRegistrationException(StatusCode.USERNAME_NOT_UNIQUE);
        }

        if (userDetailsService.isEmailTaken(request.getEmail())) {
            throw new UserRegistrationException(StatusCode.EMAIL_NOT_UNIQUE);
        }*/

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setRole(Role.USER);
        user.setPassword(request.getPassword());

        userValidator.validate(user);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userDetailsService.saveUser(user);

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(LoginUserRequestDTO request) {
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
