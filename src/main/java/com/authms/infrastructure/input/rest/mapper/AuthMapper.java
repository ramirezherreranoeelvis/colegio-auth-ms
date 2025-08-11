package com.authms.infrastructure.input.rest.mapper;

import com.authms.domain.User;
import com.authms.domain.mapper.DomainMapper;

import com.authms.infrastructure.input.rest.dto.RegisterRequest;
import com.authms.infrastructure.input.rest.dto.RegisterStudentRequest;
import com.authms.infrastructure.output.persistence.repository.interfaces.IR2dbcUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Component
public class AuthMapper {

      private final IR2dbcUserRepository userRepository;
      private final DomainMapper domainMapper;

      public Mono<User> mapToUser(RegisterRequest req) {
            log.info("mapToUser: " + req.toString());
            Mono<Optional<User>> fatherOpt = dniToOptUser(req.getDniFather() == null ? null : req.getDniFather().toString());
            Mono<Optional<User>> motherOpt = dniToOptUser(req.getDniMother() == null ? null : req.getDniMother().toString());
            Mono<Optional<User>> reprOpt = dniToOptUser(req.getDniRepresentative() == null ? null : req.getDniRepresentative().toString());
            log.info("despues de doptener los mono");
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

      public Mono<User> mapToUser(RegisterStudentRequest req) {
            log.info("mapToUser: " + req.toString());
            Mono<Optional<User>> fatherOpt = dniToOptUser(req.getDniFather() == null ? null : req.getDniFather().toString());
            Mono<Optional<User>> motherOpt = dniToOptUser(req.getDniMother() == null ? null : req.getDniMother().toString());
            Mono<Optional<User>> reprOpt = dniToOptUser(req.getDniRepresentative() == null ? null : req.getDniRepresentative().toString());
            log.info("despues de doptener los mono");
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
