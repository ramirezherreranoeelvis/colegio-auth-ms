package com.authms.infrastructure.output.security;

import com.authms.domain.TokenType;
import com.authms.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
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

      public String create(User user, TokenType tokenType) {
            log.info("create: ");
            log.info("user: " + user);
            log.info("tokenType: " + tokenType);

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
            log.info("getValue: " + jwt);
            try {
                  return parseToken(jwt);
            } catch (Exception e) {
                  throw new RuntimeException("Token inválido", e);
            }
      }

      public String getKey(String jwt) {
            log.info("getKey: " + jwt);
            try {
                  return parseToken(jwt).getId();
            } catch (Exception e) {
                  throw new RuntimeException("Token inválido", e);
            }
      }

      private Claims parseToken(String jwt) {
            log.info("parseToken: " + jwt);
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
            log.info("getSigningKey");
            var apiKeySecretBytes = Base64.getDecoder().decode(key);
            return Keys.hmacShaKeyFor(apiKeySecretBytes);
      }

}
