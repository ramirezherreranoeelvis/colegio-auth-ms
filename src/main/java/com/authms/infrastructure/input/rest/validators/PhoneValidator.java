package com.authms.infrastructure.input.rest.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;
public class PhoneValidator implements ConstraintValidator<PhoneNumber, String> {

      private static final Pattern PLAIN = Pattern.compile("^\\+51\\d{9}$");
      private boolean required;

      @Override
      public void initialize(PhoneNumber ann) {
            this.required = ann.notNull();
      }

      @Override
      public boolean isValid(String value, ConstraintValidatorContext ctx) {
            if (value == null || value.isBlank()) {
                  return !required;
            }
            String compact = value.replaceAll("\\s+", "");
            return PLAIN.matcher(compact).matches();
      }

}