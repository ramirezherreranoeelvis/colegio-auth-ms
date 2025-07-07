package com.authms.infrastructure.input.rest.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {
      com.authms.validators.ValueOfEnumStringValidator.class,
      ValueOfEnumValidator.class
})
public @interface ValueOfEnum {

      Class<? extends Enum<?>> enumClass();   // ← aquí va el <? extends Enum<?>>

      String message() default "Invalid value";

      Class<?>[] groups() default {};

      Class<? extends Payload>[] payload() default {};

}

