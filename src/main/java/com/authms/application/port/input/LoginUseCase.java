package com.authms.application.port.input;

import com.authms.domain.Token;
import reactor.core.publisher.Mono;
public interface LoginUseCase {

      Mono<Token> login(String username, String password);

}
