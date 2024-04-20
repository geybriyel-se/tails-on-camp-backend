package com.geybriyel.tailsoncamp.security;

import com.geybriyel.tailsoncamp.dto.LoginUserRequestDTO;
import com.geybriyel.tailsoncamp.dto.RegisterUserRequestDTO;
import com.geybriyel.tailsoncamp.entity.Token;
import com.geybriyel.tailsoncamp.entity.User;
import com.geybriyel.tailsoncamp.enums.Role;
import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.exception.UserRegistrationException;
import com.geybriyel.tailsoncamp.repository.TokenRepository;
import com.geybriyel.tailsoncamp.service.impl.UserDetailsServiceImpl;
import com.geybriyel.tailsoncamp.validator.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final ObjectsValidator<User> userValidator;

    private final TokenRepository tokenRepository;


    public AuthenticationResponse register(RegisterUserRequestDTO request) {
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

        userValidator.validate(user);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userDetailsService.saveUser(user);

        String jwt = jwtService.generateToken(user);

        saveUserToken(user, jwt);

        return new AuthenticationResponse(jwt);
    }

    private void saveUserToken(User user, String jwt) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    public AuthenticationResponse authenticate(LoginUserRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(user);

        revokeAllTokensByUser(user);
        saveUserToken(user, token);
        return new AuthenticationResponse(token);
    }

    private void revokeAllTokensByUser(User user) {
        List<Token> validTokenByUser = tokenRepository.findAllTokenByUser(user.getUserId());
        if (validTokenByUser.isEmpty()) {
            return;
        }

        validTokenByUser.forEach(t -> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokenByUser);
    }
}
