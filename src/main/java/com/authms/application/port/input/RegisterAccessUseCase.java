package com.authms.application.port.input;

import com.authms.domain.Access;
import com.authms.domain.User;
import reactor.core.publisher.Mono;

public interface RegisterAccessUseCase {

      Mono<Access> create(User user);
}
