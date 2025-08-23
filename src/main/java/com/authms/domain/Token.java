package com.authms.domain;

import com.authms.domain.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

      private String id;
      private String userId;
      private String value;
      private Instant expiryDate;
      private TokenType type;
      private String rol;

      public boolean isExpired() {
            return expiryDate != null && expiryDate.isBefore(Instant.now());
      }

}
