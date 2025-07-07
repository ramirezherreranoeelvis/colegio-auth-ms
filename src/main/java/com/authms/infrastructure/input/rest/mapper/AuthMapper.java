package com.authms.infrastructure.input.rest.mapper;

import com.authms.domain.User;
import com.authms.domain.mapper.DomainMapper;
import com.authms.infrastructure.input.rest.dto.RegisterRequest;
import com.authms.infrastructure.output.persistence.repository.interfaces.IR2dbcUserCrudRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class AuthMapper {

      private final IR2dbcUserCrudRepository userRepository;
      private final DomainMapper domainMapper;

      public Mono<User> mapToUser(RegisterRequest req) {

            Mono<User> fatherMono = dniToDomainUser(req.getDniFather());
            Mono<User> motherMono = dniToDomainUser(req.getDniMother());
            Mono<User> reprMono = dniToDomainUser(req.getDniRepresentative());

            /* 2) Zip: obtendremos (father, mother, representative) – cada uno puede ser null. */
            return Mono.zip(fatherMono.defaultIfEmpty(null),
                        motherMono.defaultIfEmpty(null),
                        reprMono.defaultIfEmpty(null))
                  .map(t -> {
                        User father = t.getT1();
                        User mother = t.getT2();
                        User representative = t.getT3();

                        /* 3) Construcción del objeto de dominio User. */
                        return User.builder()
                              .name(req.getName())
                              .surnamePaternal(req.getSurnamePaternal())
                              .surnameMaternal(req.getSurnameMaternal())
                              .phone(req.getPhone())
                              .dni(req.getDni().toString())
                              .rol(req.getRol())
                              .father(father)
                              .mother(mother)
                              .representative(representative)
                              .build();
                  });
      }

      private Mono<User> dniToDomainUser(String dni) {
            return dni == null
                  ? Mono.empty()
                  : userRepository.findByDni(dni)
                  .flatMap(domainMapper::mapToDomain);
      }

}
