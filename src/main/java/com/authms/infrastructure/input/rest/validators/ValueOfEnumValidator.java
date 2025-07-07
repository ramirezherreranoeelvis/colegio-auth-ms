package com.authms.infrastructure.input.rest.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, Enum<?>> {

      private Set<String> accepted;

      @Override
      public void initialize(ValueOfEnum ann) {
            accepted = Stream.of(ann.enumClass().getEnumConstants())
                  .map(Enum::name)
                  .collect(Collectors.toSet());
      }

      @Override
      public boolean isValid(Enum<?> value, ConstraintValidatorContext ctx) {
            return value == null || accepted.contains(value.name());
      }

}
