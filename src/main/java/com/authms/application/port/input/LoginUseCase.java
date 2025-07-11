package com.authms.application.port.input;

import com.authms.domain.Token;
import com.authms.domain.TokenPair;
import reactor.core.publisher.Mono;
public interface LoginUseCase {

      Mono<TokenPair> login(String username, String password);

}
