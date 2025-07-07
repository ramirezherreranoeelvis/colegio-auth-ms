package com.authms.infrastructure.input.rest.dto;

import com.authms.infrastructure.input.rest.validators.PhoneNumber;
import com.authms.infrastructure.input.rest.validators.ValueOfEnum;
import com.authms.infrastructure.output.persistence.enums.RolUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

      @NotBlank(message = "El nombre del usuario es obligatorio")
      private String name;

      @Pattern(
            regexp = "^[\\p{L}]+(?:[\\p{L} '\\-]*[\\p{L}]+)*$",
            message = "Solo se admiten letras, espacios, guiones o apóstrofes; no números"
      )
      @NotBlank(message = "El apellido Paterno del usuario es obligatorio")
      private String surnamePaternal;

      @Pattern(
            regexp = "^[\\p{L}]+(?:[\\p{L} '\\-]*[\\p{L}]+)*$",
            message = "Solo se admiten letras, espacios, guiones o apóstrofes; no números"
      )
      @NotBlank(message = "El apellido Materno del usuario es obligatorio")
      private String surnameMaternal;

      @PhoneNumber
      private String phone;

      @Digits(integer = 8, fraction = 0, message = "El dni debe tener 8 digitos")
      @NotNull(message = "El dni del usuario es obligatorio")
      private Integer dni;

      @ValueOfEnum(enumClass = RolUser.class, message = "El rol de usuario es obligatorio")
      private RolUser rol;

      private String dniFather;
      private String dniMother;
      private String dniRepresentative;

}
