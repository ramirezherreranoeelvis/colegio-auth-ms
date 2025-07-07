package com.authms.domain;

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
      private String usrId;
      private String value;
      private Instant expiryDate;
}
