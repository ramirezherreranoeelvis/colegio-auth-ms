package com.authms.validators;

import com.authms.infrastructure.input.rest.validators.ValueOfEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfEnumStringValidator
      implements ConstraintValidator<ValueOfEnum, String> {

      private Set<String> accepted;

      @Override
      public void initialize(ValueOfEnum ann) {
            accepted = Stream.of(ann.enumClass().getEnumConstants())
                  .map(e -> e.name().toUpperCase())
                  .collect(Collectors.toSet());
      }

      @Override
      public boolean isValid(String value, ConstraintValidatorContext ctx) {
            return value == null || accepted.contains(value.toUpperCase());
      }
}
