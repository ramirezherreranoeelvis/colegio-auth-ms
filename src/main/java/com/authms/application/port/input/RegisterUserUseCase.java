package com.authms.application.port.input;

import com.authms.domain.User;
import com.authms.infrastructure.input.rest.dto.register.RegisterStudentRequest;
import reactor.core.publisher.Mono;

public interface RegisterUserUseCase {

      Mono<User> registerUser(User user);

      Mono<User> registerStudent(RegisterStudentRequest user);

      Mono<User> registerTeacher(User user);

}
