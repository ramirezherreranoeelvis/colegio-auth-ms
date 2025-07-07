package com.authms.infrastructure.output.persistence.repository;

import com.authms.application.port.output.IAccessRepository;
import com.authms.application.port.output.IUserRepository;
import com.authms.domain.Access;
import com.authms.domain.User;
import com.authms.domain.mapper.DomainMapper;
import com.authms.infrastructure.output.persistence.entity.AccessEntity;
import com.authms.infrastructure.output.persistence.entity.UserEntity;
import com.authms.infrastructure.output.persistence.mapper.EntityMapper;
import com.authms.infrastructure.output.persistence.repository.interfaces.IR2dbcAccessRepository;
import com.authms.infrastructure.output.persistence.repository.interfaces.IR2dbcUserCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class R2dbcAccessRepository implements IAccessRepository {

      private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());
      private final IR2dbcAccessRepository accessRepository;
      private final DomainMapper domainMapper;
      private final EntityMapper entityMapper;

      @Override
      @Transactional
      public Mono<Access> save(Access accessDomain) {
            logger.info("Saving access: " + accessDomain);
            if (accessDomain.getId() == null) {
                  accessDomain.setId(UUID.randomUUID().toString());
            }
            logger.info("id access: " + accessDomain.getId());
            var accessEntity = entityMapper.mapToEntity(accessDomain);
            accessEntity.markNew();

            logger.info("Saving accessEntity: " + accessEntity);
            return accessRepository.save(accessEntity)
                  .map(domainMapper::mapToDomain);
      }

      @Override
      public Mono<Boolean> existsByUsername(String dni) {
            return accessRepository.existsByUsername(dni);
      }

}
