package com.authms.infrastructure.output.persistence.repository;

import com.authms.application.port.output.IUserRepository;

import com.authms.domain.User;
import com.authms.domain.mapper.DomainMapper;
import com.authms.infrastructure.output.persistence.entity.UserEntity;
import com.authms.infrastructure.output.persistence.mapper.EntityMapper;
import com.authms.infrastructure.output.persistence.repository.interfaces.IR2dbcUserCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class R2dbcUserRepository implements IUserRepository {

      private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());
      private final IR2dbcUserCrudRepository userRepository;
      private final DomainMapper domainMapper;
      private final EntityMapper entityMapper;

      @Override
      @Transactional
      public Mono<User> save(User userDomain) {
            logger.info("Saving user: " + userDomain);
            if (userDomain.getId() == null) {
                  userDomain.setId(UUID.randomUUID().toString());
            }
            logger.info("id user: " + userDomain.getId());
            UserEntity userEntity = entityMapper.mapToEntity(userDomain);
            userEntity.markNew();

            logger.info("Saving userEntity: " + userEntity);
            return userRepository.save(userEntity)
                  .flatMap(domainMapper::mapToDomain);
      }

      @Override
      public Mono<Boolean> existsByDni(String dni) {
            return userRepository.existsByDni(dni);
      }

}
