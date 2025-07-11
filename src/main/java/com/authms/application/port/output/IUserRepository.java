package com.authms.application.port.output;

import com.authms.domain.Access;
import com.authms.domain.User;
import reactor.core.publisher.Mono;
public interface IUserRepository {

      Mono<User> save(User user);

      Mono<Boolean> existsByDni(String dni);

      Mono<User> findByAccess(Access access);


}
