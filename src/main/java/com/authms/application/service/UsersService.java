package com.authms.application.service;

import com.authms.application.port.input.IFindUserUseCase;
import com.authms.application.port.input.RegisterAccessUseCase;
import com.authms.application.port.input.RegisterUserUseCase;
import com.authms.application.port.output.IUserRepository;
import com.authms.domain.enums.AuditActionType;
import com.authms.domain.User;
import com.authms.domain.exception.UserAlreadyExistsException;
import com.authms.infrastructure.config.AuditingConfig;
import com.authms.infrastructure.input.rest.dto.register.RegisterStudentRequest;
import com.authms.infrastructure.input.rest.mapper.AuthMapper;
import com.authms.infrastructure.output.notification.KafkaNotificationService;
import com.authms.infrastructure.output.notification.dto.StudentRegisteredEvent;
import com.authms.infrastructure.output.notification.dto.TeacherRegisteredEvent;
import com.authms.infrastructure.output.notification.mapper.ProducerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class UsersService implements RegisterUserUseCase, IFindUserUseCase {

      private final IUserRepository userRepository;
      private final RegisterAccessUseCase registerAccessUseCase;
      private final KafkaNotificationService kafkaNotificationService;
      private final ProducerMapper producerMapper;
      private final AuthMapper authMapper;

      @Override
      public Mono<User> registerUser(User user) {
            log.info("registerUser: " + user.toString());
            return userRepository.existsByDni(user.getDni())
                  .filter(exists -> !exists)
                  .switchIfEmpty(Mono.error(new UserAlreadyExistsException("Ya existe un usuario con ese dni registrado")))
                  .then(registerAccessUseCase.create(user))
                  .map(acc -> {
                        user.setAccess(acc);
                        return user;
                  })
                  .flatMap(user1 -> {
                        AuditingConfig.setAuditor(AuditActionType.SELF_REGISTRATION.getValue());
                        return userRepository.save(user1)
                              .doFinally(s -> AuditingConfig.clearAuditor());
                  });
      }

      @Override
      public Mono<User> registerStudent(RegisterStudentRequest request) {
            log.info("Iniciando registro para el estudiante DTO: {}", request);

            // 1. Convertir el DTO a un objeto User base (síncrono)
            User studentToRegister = authMapper.mapToUser(request);

            // 2. Buscar los IDs de los familiares de forma asíncrona
            Mono<Optional<User>> fatherOptMono = findByDni(request.getDniFather()).map(Optional::of).defaultIfEmpty(Optional.empty());
            Mono<Optional<User>> motherOptMono = findByDni(request.getDniMother()).map(Optional::of).defaultIfEmpty(Optional.empty());
            Mono<Optional<User>> reprOptMono = findByDni(request.getDniRepresentative()).map(Optional::of).defaultIfEmpty(Optional.empty());

            // 3. Cuando tengamos los resultados, completamos el objeto User y lo registramos
            return Mono.zip(fatherOptMono, motherOptMono, reprOptMono)
                  .flatMap(tuple -> {
                        Optional<User> fatherOpt = tuple.getT1();
                        Optional<User> motherOpt = tuple.getT2();
                        Optional<User> representativeOpt = tuple.getT3();

                        // Asignamos los IDs encontrados al objeto User
                        studentToRegister.setFatherId(fatherOpt.map(User::getId).orElse(null));
                        studentToRegister.setMotherId(motherOpt.map(User::getId).orElse(null));
                        studentToRegister.setRepresentativeId(representativeOpt.map(User::getId).orElse(null));

                        // 4. Continúa con la lógica de registro que ya tenías
                        return userRepository.existsByDni(studentToRegister.getDni())
                              .filter(exists -> !exists)
                              .switchIfEmpty(Mono.error(new UserAlreadyExistsException("Ya existe un estudiante con ese dni")))
                              .then(registerAccessUseCase.create(studentToRegister))
                              .map(acc -> {
                                    studentToRegister.setAccess(acc);
                                    return studentToRegister;
                              })
                              .flatMap(userRepository::save);
                  })
                  .flatMap(this::buildAndSendStudentEvent)
                  .doAfterTerminate(AuditingConfig::clearAuditor);
      }

      private Mono<User> buildAndSendStudentEvent(User student) {
            // Busca todos los DNIs aquí, en el servicio
            Mono<Optional<Integer>> fatherDniOptMono = findDniById(student.getFatherId()).map(Optional::of).defaultIfEmpty(Optional.empty());
            Mono<Optional<Integer>> motherDniOptMono = findDniById(student.getMotherId()).map(Optional::of).defaultIfEmpty(Optional.empty());
            Mono<Optional<Integer>> reprDniOptMono = findDniById(student.getRepresentativeId()).map(Optional::of).defaultIfEmpty(Optional.empty());

            // Combínalos con zip
            return Mono.zip(fatherDniOptMono, motherDniOptMono, reprDniOptMono)
                  .flatMap(tuple -> {
                        Integer fatherDni = tuple.getT1().orElse(null);
                        Integer motherDni = tuple.getT2().orElse(null);
                        Integer representativeDni = tuple.getT3().orElse(null);

                        StudentRegisteredEvent event = producerMapper.mapToProducer(student, fatherDni, motherDni, representativeDni);

                        return kafkaNotificationService.sendStudentRegisterTopic(event);
                  })
                  .thenReturn(student);
      }

      /**
       * Busca un usuario por su ID y devuelve su DNI.
       *
       * @param userId El ID del usuario a buscar.
       * @return Un Mono<String> con el DNI, o un Mono vacío si el ID es nulo o no se encuentra el usuario.
       */
      @Override
      public Mono<Integer> findDniById(String userId) {
            return Mono.justOrEmpty(userId) // Solo procede si el ID no es nulo
                  .flatMap(userRepository::findById)
                  .map(User::getDni);
      }

      @Override
      public Mono<User> registerTeacher(User user) {
            log.info("Iniciando registro para el profesor: {}", user);

            return userRepository.existsByDni(user.getDni())
                  .filter(exists -> !exists)
                  .switchIfEmpty(Mono.error(new UserAlreadyExistsException("Ya existe un usuario con ese DNI registrado")))
                  .then(registerAccessUseCase.create(user))
                  .map(access -> {
                        user.setAccess(access);
                        return user;
                  })
                  .flatMap(userToSave -> {
                        AuditingConfig.setAuditor(AuditActionType.ADMIN.getValue());
                        return userRepository.save(userToSave);
                  })
                  .flatMap(savedTeacher -> {
                        TeacherRegisteredEvent event = producerMapper.mapToProducer(savedTeacher);

                        return kafkaNotificationService.sendTeacherRegisterTopic(event)
                              .thenReturn(savedTeacher);
                  })
                  .doAfterTerminate(AuditingConfig::clearAuditor);
      }

      @Override
      public Mono<User> findById(String id) {
            return Mono.justOrEmpty(id)
                  .flatMap(userRepository::findById);
      }

      @Override
      public Mono<User> findByDni(Integer dni) {
            return Mono.justOrEmpty(dni)
                  .flatMap(userRepository::findByDni);
      }

}
