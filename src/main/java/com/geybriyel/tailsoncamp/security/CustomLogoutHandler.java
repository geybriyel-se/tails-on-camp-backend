package com.geybriyel.tailsoncamp.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.geybriyel.tailsoncamp.entity.Token;
import com.geybriyel.tailsoncamp.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);

        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        if (storedToken != null) {
            storedToken.setLoggedOut(true);
            tokenRepository.save(storedToken);

            response.setContentType("application/json;charset=UTF-8");
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                response.getWriter().write(
                        objectMapper.writeValueAsString(
                                objectMapper.createObjectNode()
                                        .put("timestamp", Instant.now().toString())
                                        .put("message", "Successfully logged out.")
                        )
                );
            } catch (IOException e) {
                response.setStatus(500);
            }
        }

    }
}
