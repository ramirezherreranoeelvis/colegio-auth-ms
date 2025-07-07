package com.authms.infrastructure.output.persistence.repository.interfaces;

import com.authms.infrastructure.output.persistence.entity.AccessEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface IR2dbcAccessRepository extends ReactiveCrudRepository<AccessEntity, String> {
      Mono<Boolean> existsByUsername(String username);
}
