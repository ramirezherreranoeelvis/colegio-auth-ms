package com.authms.application.service;

import com.authms.application.port.input.LoginUseCase;
import com.authms.application.port.input.RefreshTokenUseCase;
import com.authms.application.port.output.IAccessRepository;
import com.authms.application.port.output.IPasswordEncoder;
import com.authms.domain.*;
import com.authms.domain.enums.TokenType;
import com.authms.domain.exception.AuthenticationException;
import com.authms.domain.mapper.DomainMapper;
import com.authms.infrastructure.output.KafkaNotificationService;
import com.authms.infrastructure.output.security.IJwtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Log4j2
@RequiredArgsConstructor
@Service
public class AuthenticationService implements LoginUseCase, RefreshTokenUseCase {

      private final IAccessRepository accessRepository;
      private final IPasswordEncoder passwordEncoder;
      private final IJwtRepository jwtRepository;
      private final DomainMapper domainMapper;
      private final KafkaNotificationService kafkaNotificationService;

      @Override
      public Mono<TokenPair> login(String username, String password) {
            return this.accessRepository.findByUsername(username).switchIfEmpty(Mono.error(new AuthenticationException("Usuario no existente"))).filter(access -> passwordEncoder.matches(password, access.getPassword())).switchIfEmpty(Mono.error(new AuthenticationException("Contraseña inválida"))).flatMap(domainMapper::mapToDomainUser).map(user -> {
                  var accessTokenValue = jwtRepository.generateToken(user, TokenType.ACCESS);
                  var refreshTokenValue = jwtRepository.generateToken(user, TokenType.REFRESH);

                  var accessToken = Token.builder().userId(user.getId()).value(accessTokenValue).expiryDate(Instant.now().plus(30, ChronoUnit.MINUTES)).type(TokenType.ACCESS).rol(user.getRol().toString()).build();

                  var refreshToken = Token.builder().userId(user.getId()).value(refreshTokenValue).expiryDate(Instant.now().plus(7, ChronoUnit.DAYS)).type(TokenType.REFRESH).rol(user.getRol().toString()).build();
                  log.info("antes");

                  this.kafkaNotificationService.sendWelcome(user, accessToken);
                  log.info("despues");
                  return TokenPair.builder().accessToken(accessToken).refreshToken(refreshToken).build();
            });

      }

      @Override
      public Mono<Token> refreshToken(String refreshToken) {
            return null;
      }

}
