package com.authms.infrastructure.input.rest.mapper;

import com.authms.domain.User;
import com.authms.infrastructure.input.rest.dto.register.RegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapper {

      /**
       * Convierte un objeto User del dominio a una respuesta HTTP exitosa (200 OK).
       * IMPORTANTE: Por seguridad, la contraseña NUNCA se incluye en la respuesta.
       *
       * @param user El usuario que fue guardado exitosamente.
       * @return Un ResponseEntity que contiene el ID y username del nuevo usuario.
       */
      public ResponseEntity<RegisterResponse> toRegisterResponse(User user) {

            RegisterResponse responseBody = RegisterResponse.builder()
                  .username(user.getAccess().getUsername())
                  .password(user.getAccess().getPassword())
                  .build();

            return ResponseEntity.ok(responseBody);
      }
}