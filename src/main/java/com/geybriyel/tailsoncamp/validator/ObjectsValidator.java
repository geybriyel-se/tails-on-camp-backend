package com.geybriyel.tailsoncamp.validator;

import com.geybriyel.tailsoncamp.enums.StatusCode;
import com.geybriyel.tailsoncamp.exception.ObjectNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectsValidator<T> {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    public void validate(T object) {
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(object);

        if (!constraintViolationSet.isEmpty()) {
            var errorMessages = constraintViolationSet.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            throw new ObjectNotValidException(StatusCode.BAD_REQUEST, errorMessages);
        }

    }
}
