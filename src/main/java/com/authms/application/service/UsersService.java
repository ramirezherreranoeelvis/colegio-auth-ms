package com.authms.application.service;

import com.authms.application.port.input.RegisterAccessUseCase;
import com.authms.application.port.input.RegisterUserUseCase;
import com.authms.application.port.output.IUserRepository;
import com.authms.domain.enums.AuditActionType;
import com.authms.domain.User;
import com.authms.domain.exception.UserAlreadyExistsException;
import com.authms.infrastructure.config.AuditingConfig;
import com.authms.infrastructure.output.KafkaNotificationService;
import com.authms.infrastructure.output.persistence.enums.RolUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
public class UsersService implements RegisterUserUseCase {

      private final IUserRepository userRepository;
      private final RegisterAccessUseCase registerAccessUseCase;
      private final KafkaNotificationService kafkaNotificationService;


      @Override
      public Mono<User> registerUser(User user) {
            log.info("registerUser: " + user.toString());
            return this.userRepository.existsByDni(user.getDni())
                  .filter(exists -> !exists)
                  .switchIfEmpty(Mono.error(new UserAlreadyExistsException("Ya existe un usuario con ese dni registrado")))
                  .then(registerAccessUseCase.create(user))
                  .map(acc -> {
                        user.setAccess(acc);
                        return user;
                  })
                  .flatMap(user1 -> {
                        AuditingConfig.setAuditor(AuditActionType.SELF_REGISTRATION.getValue());
                        return this.userRepository.save(user1).doFinally(s -> AuditingConfig.clearAuditor());
                  });
      }

      @Override
      public Mono<User> registerStudent(User user) {
            log.info("registerStudent: " + user.toString());
            return this.userRepository.existsByDni(user.getDni())
                  .filter(exists -> !exists)
                  .switchIfEmpty(Mono.error(new UserAlreadyExistsException("Ya existe un usuario con ese dni registrado")))
                  .then(registerAccessUseCase.create(user))
                  .map(acc -> {
                        user.setAccess(acc);
                        return user;
                  })
                  .flatMap(user1 -> {
                        AuditingConfig.setAuditor(AuditActionType.SELF_REGISTRATION.getValue());
                        return this.userRepository.save(user1).doOnSuccess(savedUser -> {
                              if (savedUser.getRol() == RolUser.STUDENT) {
                                    log.info("El usuario es un estudiante. Enviando evento a Kafka...");
                                    kafkaNotificationService.sendStudentRegisterTopic(savedUser);
                              }
                        }).doFinally(s -> AuditingConfig.clearAuditor());
                  });
      }

      @Override
      public Mono<User> registerTeacher(User user) {
            log.info("Iniciando registro para el profesor: {}", user);

            return this.userRepository.existsByDni(user.getDni())
                  .filter(exists -> !exists)
                  .switchIfEmpty(Mono.error(new UserAlreadyExistsException("Ya existe un usuario con ese DNI registrado")))
                  .then(registerAccessUseCase.create(user))
                  .map(access -> {
                        user.setAccess(access);
                        return user;
                  })
                  .flatMap(userToSave -> {
                        AuditingConfig.setAuditor(AuditActionType.ADMIN.getValue());

                        return this.userRepository.save(userToSave).doOnSuccess(savedUser -> {
                              log.info("Profesor registrado exitosamente. Enviando evento a Kafka...");
                              kafkaNotificationService.sendTeacherRegisterTopic(savedUser);
                        }).doFinally(signalType -> AuditingConfig.clearAuditor());
                  });
      }

}
