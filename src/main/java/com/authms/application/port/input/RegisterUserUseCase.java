package com.authms.application.port.input;

import com.authms.domain.User;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface RegisterUserUseCase {

      Mono<User> registerUser(User user);

}
