package com.authms.application.port.input;

import com.authms.domain.Token;
import reactor.core.publisher.Mono;

public interface RefreshTokenUseCase {

      Mono<Token> refreshToken(String refreshToken);

}
