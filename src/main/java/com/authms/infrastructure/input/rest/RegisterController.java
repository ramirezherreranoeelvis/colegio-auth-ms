package com.authms.infrastructure.input.rest;

import com.authms.application.port.input.RegisterUserUseCase;
import com.authms.infrastructure.input.rest.dto.register.RegisterRequest;
import com.authms.infrastructure.input.rest.dto.register.RegisterResponse;
import com.authms.infrastructure.input.rest.dto.register.RegisterStudentRequest;
import com.authms.infrastructure.input.rest.dto.register.RegisterTeacherRequest;
import com.authms.infrastructure.input.rest.mapper.AuthMapper;
import com.authms.infrastructure.input.rest.mapper.ResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/api/register")
@RestController
@RequiredArgsConstructor
@Log4j2
public class RegisterController {

      private final RegisterUserUseCase registerUserUseCase;
      private final AuthMapper authMapper;
      private final ResponseMapper responseMapper;

      @PostMapping("/user")
      public Mono<ResponseEntity<RegisterResponse>> user(@Valid @RequestBody RegisterRequest request) {
            return authMapper.mapToUser(request)
                  .flatMap(registerUserUseCase::registerUser)
                  .map(responseMapper::toRegisterResponse);
      }

      @PostMapping("/student")
      public Mono<ResponseEntity<RegisterResponse>> student(@Valid @RequestBody RegisterStudentRequest request) {
            return registerUserUseCase.registerStudent(request)
                  .map(responseMapper::toRegisterResponse);
      }

      @PostMapping("/teacher")
      public Mono<ResponseEntity<RegisterResponse>> teacher(@Valid @RequestBody RegisterTeacherRequest request) {
            return authMapper.mapToUser(request)
                  .flatMap(registerUserUseCase::registerTeacher)
                  .map(responseMapper::toRegisterResponse);

      }

}
