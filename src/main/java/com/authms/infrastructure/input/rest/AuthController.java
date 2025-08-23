package com.authms.infrastructure.input.rest;

import com.authms.application.port.input.LoginUseCase;
import com.authms.application.port.input.RefreshTokenUseCase;
import com.authms.infrastructure.input.rest.dto.login.LoginRequest;
import com.authms.infrastructure.input.rest.dto.login.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
@Log4j2
class AuthController {

      private final LoginUseCase loginUseCase;
      private final RefreshTokenUseCase refreshTokenUseCase;

      @PostMapping(
            "/login")
      public Mono<ResponseEntity<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
            return loginUseCase.login(request.getUsername(), request.getPassword())
                  .map(tokenPair -> ResponseEntity.ok(
                        LoginResponse.builder()
                              .accessToken(tokenPair.getAccessToken().getValue())
                              .refreshToken(tokenPair.getRefreshToken().getValue())
                              .expiresIn(tokenPair.getAccessToken().getExpiryDate())
                              .tokenType("Bearer")
                              .rol(tokenPair.getAccessToken().getRol())
                              .build()
                  ));
      }

}
