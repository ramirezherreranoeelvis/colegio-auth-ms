package com.authms.infrastructure.output.persistence.repository.interfaces;

import com.authms.infrastructure.output.persistence.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IR2dbcUserRepository extends ReactiveCrudRepository<UserEntity, String> {

      Mono<Boolean> existsByDni(Integer dni);

      Mono<UserEntity> findByDni(Integer dni);

      Mono<UserEntity> findByIdAccess(String accessId);

}
