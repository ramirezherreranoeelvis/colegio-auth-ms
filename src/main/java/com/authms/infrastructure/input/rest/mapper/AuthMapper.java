package com.authms.infrastructure.input.rest.mapper;

import com.authms.domain.User;
import com.authms.domain.mapper.DomainMapper;
import com.authms.infrastructure.config.Logger;
import com.authms.infrastructure.input.rest.dto.RegisterRequest;
import com.authms.infrastructure.output.persistence.repository.interfaces.IR2dbcUserCrudRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@AllArgsConstructor
@Component
public class AuthMapper {

      private final IR2dbcUserCrudRepository userRepository;
      private final DomainMapper domainMapper;
      private final Logger logger;

      public Mono<User> mapToUser(RegisterRequest req) {
            logger.log("mapToUser: " + req.toString());
            Mono<Optional<User>> fatherOpt = dniToOptUser(req.getDniFather() == null ? null : req.getDniFather().toString());
            Mono<Optional<User>> motherOpt = dniToOptUser(req.getDniMother() == null ? null : req.getDniMother().toString());
            Mono<Optional<User>> reprOpt = dniToOptUser(req.getDniRepresentative() == null ? null : req.getDniRepresentative().toString());
            logger.log("despues de doptener los mono");
            /* 2) Zip: obtendremos (father, mother, representative) – cada uno puede ser null. */
            return Mono.zip(fatherOpt, motherOpt, reprOpt)
                  .map(t -> {
                        User father = t.getT1().orElse(null);
                        User mother = t.getT2().orElse(null);
                        User representative = t.getT3().orElse(null);

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

      private Mono<Optional<User>> dniToOptUser(String dni) {
            return dni == null
                  ? Mono.just(Optional.empty())
                  : userRepository.findByDni(dni)
                  .flatMap(domainMapper::mapToDomain)
                  .map(Optional::of)
                  .switchIfEmpty(Mono.just(Optional.empty()));
      }

}
