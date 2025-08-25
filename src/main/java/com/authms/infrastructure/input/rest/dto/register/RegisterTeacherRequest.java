package com.authms.infrastructure.input.rest.dto.register;

import com.authms.infrastructure.input.rest.validators.PhoneNumber;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTeacherRequest {

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

}
