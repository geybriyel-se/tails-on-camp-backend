package com.geybriyel.tailsoncamp.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

//@Documented
//@Constraint(validatedBy = ReservedUsername.ReservedUsernameValidator.class)
//@Target({ElementType.FIELD})
//@Retention(RetentionPolicy.RUNTIME)
public @interface ReservedUsername {
//    String message() default "Username is a reserved word.";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//
//    class ReservedUsernameValidator implements ConstraintValidator<ReservedUsername, String> {
//
//        @Override
//        public void initialize(ReservedUsername constraintAnnotation) {
//        }
//
//        @Override
//        public boolean isValid(String username, ConstraintValidatorContext context) {
//            if (isReserved(username)) {
//                context.disableDefaultConstraintViolation();
//                context.buildConstraintViolationWithTemplate("Username '" + username + "' is reserved.")
//                        .addConstraintViolation();
//                return false;
//            }
//            return true;
//        }
//
//        private boolean isReserved(String username) {
//            return username != null && (username.equalsIgnoreCase("root") || username.equalsIgnoreCase("admin") || username.equalsIgnoreCase("user"));
//        }
//    }
}
