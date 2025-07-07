package com.authms.infrastructure.input.rest;

import com.authms.application.port.input.LoginUseCase;
import com.authms.application.port.input.RefreshTokenUseCase;
import com.authms.application.port.input.RegisterUserUseCase;
import com.authms.infrastructure.input.rest.dto.LoginRequest;
import com.authms.infrastructure.input.rest.dto.LoginResponse;
import com.authms.infrastructure.input.rest.dto.RegisterRequest;
import com.authms.infrastructure.input.rest.dto.RegisterResponse;
import com.authms.infrastructure.input.rest.mapper.AuthMapper;
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
      private final RegisterUserUseCase registerUserUseCase;
      private final AuthMapper authMapper;

      @PostMapping("/login")
      public Mono<ResponseEntity<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
            return Mono.just(ResponseEntity.ok(LoginResponse.builder().build()));
      }

      @PostMapping("/register")
      public Mono<ResponseEntity<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
            return authMapper.mapToUser(request)
                  .flatMap(registerUserUseCase::registerUser)
                  .map(user -> ResponseEntity.ok(
                        RegisterResponse.builder()
                              .username(user.getAccess().getUsername())
                              .password(user.getAccess().getPassword())
                              .build()));
      }

}
