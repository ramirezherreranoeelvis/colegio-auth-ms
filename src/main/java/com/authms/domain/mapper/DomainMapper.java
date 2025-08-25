package com.authms.domain.mapper;

import com.authms.domain.Access;
import com.authms.domain.User;
import com.authms.infrastructure.output.persistence.entity.AccessEntity;
import com.authms.infrastructure.output.persistence.entity.UserEntity;
import com.authms.infrastructure.output.persistence.repository.interfaces.IR2dbcAccessRepository;
import com.authms.infrastructure.output.persistence.repository.interfaces.IR2dbcUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class DomainMapper {

      private final IR2dbcAccessRepository r2dbcAccessRepository;
      private final IR2dbcUserRepository r2dbcUserRepository;

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
                        .fatherId(userEntity.getIdFather())
                        .motherId(userEntity.getIdMother())
                        .representativeId(userEntity.getIdRepresentative())
                        .access(access)
                        .build());
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

      public Mono<User> mapToDomainUser(Access access) {
            return r2dbcUserRepository.findByIdAccess(access.getId())
                  .map(userEntity -> User.builder()
                        .id(userEntity.getId())
                        .dni(userEntity.getDni())
                        .name(userEntity.getName())
                        .surnamePaternal(userEntity.getSurnamePaternal())
                        .surnameMaternal(userEntity.getSurnameMaternal())
                        .phone(userEntity.getPhone())
                        .rol(userEntity.getRol())
                        .fatherId(userEntity.getIdFather())
                        .motherId(userEntity.getIdMother())
                        .representativeId(userEntity.getIdRepresentative())
                        .access(access)
                        .build());
      }

}
