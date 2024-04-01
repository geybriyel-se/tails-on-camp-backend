package com.geybriyel.tailsoncamp.validator;

import com.geybriyel.tailsoncamp.annotations.UniqueUsername;
import com.geybriyel.tailsoncamp.service.impl.UserDetailsServiceImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private UserDetailsServiceImpl userDetailsService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        log.info("checking if username {} is unique", username);
        return !userDetailsService.isUsernameTaken(username);
    }

}
