package com.authms.infrastructure.output.persistence.repository;

import com.authms.application.port.output.IUserRepository;

import com.authms.domain.Access;
import com.authms.domain.User;
import com.authms.domain.mapper.DomainMapper;
import com.authms.infrastructure.output.persistence.entity.UserEntity;
import com.authms.infrastructure.output.persistence.mapper.EntityMapper;
import com.authms.infrastructure.output.persistence.repository.interfaces.IR2dbcUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Log4j2
@Repository
@RequiredArgsConstructor
public class R2dbcUserRepository implements IUserRepository {

      private final IR2dbcUserRepository userRepository;
      private final DomainMapper domainMapper;
      private final EntityMapper entityMapper;

      @Override
      @Transactional
      public Mono<User> save(User userDomain) {
            log.info("Saving user: " + userDomain);
            if (userDomain.getId() == null) {
                  userDomain.setId(UUID.randomUUID().toString());
            }
            log.info("id user: " + userDomain.getId());
            UserEntity userEntity = entityMapper.mapToEntity(userDomain);
            userEntity.markNew();

            log.info("Saving userEntity: " + userEntity);
            return userRepository.save(userEntity)
                  .flatMap(domainMapper::mapToDomain);
      }

      @Override
      @Transactional
      public Mono<Boolean> existsByDni(String dni) {
            return userRepository.existsByDni(dni);
      }

      @Override
      public Mono<User> findByAccess(Access access) {
            return this.userRepository.findByIdAccess(access.getId())
                  .flatMap(domainMapper::mapToDomain);
      }

}
