package com.authms.application.port.input;

import com.authms.domain.User;
import reactor.core.publisher.Mono;
public interface IFindUserUseCase {

      Mono<User> findById(String id);

      Mono<User> findByDni(Integer dni);

      Mono<Integer> findDniById(String userId);
}
