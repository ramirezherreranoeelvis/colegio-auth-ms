package com.authms.application.port.output;

import com.authms.domain.Access;
import com.authms.domain.User;
import reactor.core.publisher.Mono;
public interface IUserRepository {

      Mono<User> save(User user);

      Mono<Boolean> existsByDni(Integer dni);

      Mono<User> findByAccess(Access access);

      Mono<User> findById(String id);

      Mono<User> findByDni(Integer dni);

}
