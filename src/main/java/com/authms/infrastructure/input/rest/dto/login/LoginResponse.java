package com.authms.infrastructure.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

      private String accessToken;
      private String refreshToken;
      private Instant expiresIn;
      @Builder.Default
      private String tokenType = "Bearer";
      private String rol;

}
