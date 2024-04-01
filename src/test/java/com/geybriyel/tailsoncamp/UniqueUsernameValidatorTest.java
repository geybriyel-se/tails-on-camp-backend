package com.geybriyel.tailsoncamp;

import com.geybriyel.tailsoncamp.service.impl.UserDetailsServiceImpl;
import com.geybriyel.tailsoncamp.validator.UniqueUsernameValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UniqueUsernameValidatorTest {

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    private UniqueUsernameValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        validator = new UniqueUsernameValidator(userDetailsService);
    }

    @Test
    void isValid_UniqueUsername_ReturnsTrue() {
        String username = "uniqueUsername";
        when(userDetailsService.isUsernameTaken(username)).thenReturn(false);

        assertTrue(validator.isValid(username, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void isValid_DuplicateUsername_ReturnsFalse() {
        String username = "duplicateUsername";
        when(userDetailsService.isUsernameTaken(username)).thenReturn(true);

        assertFalse(validator.isValid(username, mock(ConstraintValidatorContext.class)));
    }
}
