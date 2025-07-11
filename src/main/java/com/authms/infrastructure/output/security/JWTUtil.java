package com.authms.infrastructure.output.security;

import com.authms.domain.TokenType;
import com.authms.domain.User;
import com.authms.infrastructure.config.Logger;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JWTUtil {

      @Value("${security.jwt.secret}")
      private String key;
      @Value("${security.jwt.issuer}")
      private String issuer;
      @Value("${security.jwt.ttlMillis}")
      private long timeExpired;
      @Value("${security.jwt.ttMillisExpired}")
      private long timeExpiredRefresh;

      private final Logger logger;

      public String create(User user, TokenType tokenType) {
            logger.log("create: ");
            logger.log("user: " + user);
            logger.log("tokenType: " + tokenType);

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("rol", user.getRol().name());
            claims.put("type", tokenType.name());

            long expirationMs = tokenType == TokenType.ACCESS ? timeExpired : timeExpiredRefresh;
            var creationTime = new Date(System.currentTimeMillis());

            return Jwts.builder()
                  .claims(claims)
                  .subject(user.getAccess().getUsername())
                  .issuedAt(creationTime)
                  .issuer(issuer)
                  .expiration(new Date(creationTime.getTime() + expirationMs))
                  .signWith(getSigningKey())
                  .compact();
      }

      public Claims getValue(String jwt) {
            logger.log("getValue: " + jwt);
            try {
                  return parseToken(jwt);
            } catch (Exception e) {
                  throw new RuntimeException("Token inválido", e);
            }
      }

      public String getKey(String jwt) {
            logger.log("getKey: " + jwt);
            try {
                  return parseToken(jwt).getId();
            } catch (Exception e) {
                  throw new RuntimeException("Token inválido", e);
            }
      }

      private Claims parseToken(String jwt) {
            logger.log("parseToken: " + jwt);
            try {
                  return Jwts.parser()
                        .verifyWith(getSigningKey())
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
            } catch (JwtException e) {
                  throw new JwtException("Token inválido: " + e.getMessage());
            }
      }

      private SecretKey getSigningKey() {
            logger.log("getSigningKey");
            var apiKeySecretBytes = Base64.getDecoder().decode(key);
            return Keys.hmacShaKeyFor(apiKeySecretBytes);
      }

}
