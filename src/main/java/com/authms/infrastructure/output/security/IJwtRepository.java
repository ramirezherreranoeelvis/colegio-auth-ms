package com.authms.infrastructure.output.security;

import com.authms.domain.enums.TokenType;
import com.authms.domain.User;
import io.jsonwebtoken.Claims;
public interface IJwtRepository {

      String generateToken(User user, TokenType tokenType);

      String getUsernameFromToken(String token);

      boolean validateToken(String token);

      void deleteToken(String token);

      String getRefreshTokenFromToken(String token);

      void deleteRefreshToken(String token);

      String generateRefreshToken(String id, String username);

      boolean validateRefreshToken(String token);

      String getUserIdFromToken(String token);

      String getUserIdFromRefreshToken(String token);

      Claims getValue(String token);

}
