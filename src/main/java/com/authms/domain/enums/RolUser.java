package com.authms.domain.enums;

import java.util.Arrays;
public enum RolUser {
      STUDENT, TEACHER, ASSISTANT, DIRECTOR, FATHER, MOTHER, REPRESENTATIVE;

      public static RolUser from(String raw) {
            return Arrays.stream(values())
                  .filter(r -> r.name().equalsIgnoreCase(raw))
                  .findFirst()
                  .orElseThrow(() ->
                        new IllegalArgumentException("Rol inválido: " + raw));
      }
}
