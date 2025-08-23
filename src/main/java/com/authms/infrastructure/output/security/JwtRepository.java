package com.authms.infrastructure.output.security;

import com.authms.domain.enums.TokenType;
import com.authms.domain.User;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("jwtRepository")
public class JwtRepository implements IJwtRepository {

      private final JWTUtil jwtUtil;

      @Override
      public String generateToken(User user, TokenType tokenType) {
            return this.jwtUtil.create(user, tokenType);
      }

      @Override
      public String getUsernameFromToken(String token) {
            return "";
      }

      @Override
      public boolean validateToken(String token) {
            return false;
      }

      @Override
      public void deleteToken(String token) {

      }

      @Override
      public String getRefreshTokenFromToken(String token) {
            return "";
      }

      @Override
      public void deleteRefreshToken(String token) {

      }

      @Override
      public String generateRefreshToken(String id, String username) {
            return "";
      }

      @Override
      public boolean validateRefreshToken(String token) {
            return false;
      }

      @Override
      public String getUserIdFromToken(String token) {
            return "";
      }

      @Override
      public String getUserIdFromRefreshToken(String token) {
            return "";
      }

      @Override
      public Claims getValue(String token) {
            return null;
      }

}
