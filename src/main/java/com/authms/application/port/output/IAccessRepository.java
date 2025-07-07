package com.authms.application.port.output;

import com.authms.domain.Access;
import reactor.core.publisher.Mono;
public interface IAccessRepository {

      Mono<Access> save(Access access);

      Mono<Boolean> existsByUsername(String username);

}
