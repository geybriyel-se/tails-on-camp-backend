package com.geybriyel.tailsoncamp.utils;

import com.geybriyel.tailsoncamp.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityUtils {

    public static User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return ((User) authentication.getPrincipal());
        }
        return null;
    }

    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }

    public static boolean hasAnyRole(String... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> {
                    for (String role : roles) {
                        if (authority.getAuthority().equals(role)) {
                            return true;
                        }
                    }
                    return false;
                });
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public static List<String> getCurrentUserAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    public static boolean hasAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(authority));
    }

    // Check if the current user has any of the specified authorities
    public static boolean hasAnyAuthority(String... authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> Arrays.asList(authorities).contains(authority));
    }

    public static String getUserUsername() {
        User loggedInUser = getLoggedInUser();
        if (loggedInUser != null) {
            return loggedInUser.getUsername();
        }
        return null;
    }

    public static String getUserEmail() {
        User loggedInUser = getLoggedInUser();
        if (loggedInUser != null) {
            return loggedInUser.getEmail();
        }
        return null;
    }

    public static Long getUserUserId() {
        User loggedInUser = getLoggedInUser();
        if (loggedInUser != null) {
            return loggedInUser.getUserId();
        }
        return null;
    }

    public static String getCurrentUserFullName() {
        User loggedInUser = getLoggedInUser();
        if (loggedInUser != null) {
            return loggedInUser.getFirstName() + " " + loggedInUser.getLastName();
        }
        return null;
    }

}
