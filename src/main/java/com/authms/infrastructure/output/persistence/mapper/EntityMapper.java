package com.authms.infrastructure.output.persistence.mapper;

import com.authms.domain.Access;
import com.authms.domain.User;
import com.authms.infrastructure.output.persistence.entity.AccessEntity;
import com.authms.infrastructure.output.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

      public UserEntity mapToEntity(User userDomain) {
            return UserEntity.builder()
                  .id(userDomain.getId())
                  .dni(userDomain.getDni())
                  .name(userDomain.getName())
                  .surnamePaternal(userDomain.getSurnamePaternal())
                  .surnameMaternal(userDomain.getSurnameMaternal())
                  .phone(userDomain.getPhone())
                  .rol(userDomain.getRol())
                  .idAccess(userDomain.getAccess().getId())
                  .idFather(userDomain.getFatherId())
                  .idMother(userDomain.getMotherId())
                  .idRepresentative(userDomain.getRepresentativeId())
                  .createdBy(userDomain.getCreatedBy())
                  .createdDate(userDomain.getCreatedDate())
                  .lastModifiedBy(userDomain.getLastModifiedBy())
                  .lastModifiedDate(userDomain.getLastModifiedDate())
                  .build();
      }

      public AccessEntity mapToEntity(Access access) {
            return AccessEntity.builder()
                  .id(access.getId())
                  .accessEnabled(access.getAccessEnabled())
                  .username(access.getUsername())
                  .password(access.getPassword())
                  .description(access.getDescription())
                  .build();
      }

}
