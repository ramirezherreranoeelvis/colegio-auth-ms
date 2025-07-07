package com.authms.infrastructure.input.rest.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

        String message() default "Número de teléfono inválido";

        boolean notNull() default false;

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

}

