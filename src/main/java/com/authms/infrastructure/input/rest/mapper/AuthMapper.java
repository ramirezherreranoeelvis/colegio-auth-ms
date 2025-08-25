package com.authms.infrastructure.input.rest.mapper;

import com.authms.application.port.input.IFindUserUseCase;
import com.authms.domain.User;

import com.authms.infrastructure.input.rest.dto.register.RegisterRequest;
import com.authms.infrastructure.input.rest.dto.register.RegisterStudentRequest;
import com.authms.infrastructure.input.rest.dto.register.RegisterTeacherRequest;
import com.authms.domain.enums.RolUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Component
public class AuthMapper {

      public Mono<User> mapToUser(RegisterRequest req) {
            return Mono.just(
                  User.builder()
                        .name(req.getName())
                        .surnamePaternal(req.getSurnamePaternal())
                        .surnameMaternal(req.getSurnameMaternal())
                        .phone(req.getPhone())
                        .dni(req.getDni())
                        .rol(req.getRol())
                        .build()
            );


      }

      public User mapToUser(RegisterStudentRequest req) {
            return User.builder()
                  .name(req.getName())
                  .surnamePaternal(req.getSurnamePaternal())
                  .surnameMaternal(req.getSurnameMaternal())
                  .phone(req.getPhone())
                  .dni(req.getDni())
                  .rol(RolUser.STUDENT)
                  .build();
      }

      public Mono<User> mapToUser(RegisterTeacherRequest req) {
            log.info("mapToUser: " + req.toString());
            log.info("despues de doptener los mono");

            return Mono.just(
                  User.builder()
                        .name(req.getName())
                        .surnamePaternal(req.getSurnamePaternal())
                        .surnameMaternal(req.getSurnameMaternal())
                        .phone(req.getPhone())
                        .dni(req.getDni())
                        .rol(RolUser.TEACHER)
                        .build()
            );
      }

}