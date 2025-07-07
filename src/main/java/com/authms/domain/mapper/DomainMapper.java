package com.authms.domain.mapper;

import com.authms.application.port.output.IAccessRepository;
import com.authms.domain.Access;
import com.authms.domain.User;
import com.authms.infrastructure.output.persistence.entity.AccessEntity;
import com.authms.infrastructure.output.persistence.entity.UserEntity;
import com.authms.infrastructure.output.persistence.repository.interfaces.IR2dbcAccessRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class DomainMapper {

      private final IR2dbcAccessRepository r2dbcAccessRepository;

      public Mono<User> mapToDomain(UserEntity userEntity) {
            return r2dbcAccessRepository.findById(userEntity.getIdAccess())
                  .map(this::mapToDomain)
                  .map(access -> User.builder()
                        .id(userEntity.getId())
                        .dni(userEntity.getDni())
                        .name(userEntity.getName())
                        .surnamePaternal(userEntity.getSurnamePaternal())
                        .surnameMaternal(userEntity.getSurnameMaternal())
                        .phone(userEntity.getPhone())
                        .access(access)
                        .build())
                  ;
      }

      public Access mapToDomain(AccessEntity accessEntity) {
            return Access.builder()
                  .id(accessEntity.getId())
                  .accessEnabled(accessEntity.getAccessEnabled())
                  .username(accessEntity.getUsername())
                  .password(accessEntity.getPassword())
                  .description(accessEntity.getDescription())
                  .build();
      }

}
