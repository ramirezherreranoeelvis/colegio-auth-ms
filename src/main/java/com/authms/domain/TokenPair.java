package com.authms.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenPair {

      private Token accessToken;
      private Token refreshToken;

}
