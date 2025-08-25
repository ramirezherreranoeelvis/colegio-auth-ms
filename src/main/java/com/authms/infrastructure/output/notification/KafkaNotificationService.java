package com.authms.infrastructure.output.notification;

import com.authms.domain.Token;
import com.authms.domain.User;
import com.authms.infrastructure.output.notification.dto.StudentRegisteredEvent;
import com.authms.infrastructure.output.notification.dto.UserWelcome;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class KafkaNotificationService {

      private final KafkaTemplate<String, String> kafkaTemplate;
      private final ObjectMapper objectMapper;
      private final String welcomeTopic;
      private final String studentRegisterTopic;
      private final String teacherRegisterTopic;

      public void sendWelcome(UserWelcome user) {
            try {
                  String body = objectMapper.writeValueAsString(user);
                  kafkaTemplate.send(welcomeTopic, body);

                  log.info("Welcome email event sent for user: {}", user.username());
            } catch (Exception e) {
                  log.error("Failed to send welcome email event: {}", e.getMessage());
            }
      }

      public Mono<Void> sendStudentRegisterTopic(StudentRegisteredEvent user) {
            return Mono.fromRunnable(() -> {
                        try {
                              String body = objectMapper.writeValueAsString(user);
                              kafkaTemplate.send(studentRegisterTopic, body);
                              log.info("Evento de registro de estudiante enviado para el id: {}", user.id());
                        } catch (Exception e) {
                              throw new KafkaProducerException(null, "Fallo al enviar evento de registro para estudiante: " + user.id(), e);
                        }
                  })
                  .subscribeOn(Schedulers.boundedElastic())
                  .then();
      }

      public void sendTeacherRegisterTopic(User user) {
            try {
                  Map<String, String> event = Map.of(
                        "id", user.getId(),
                        "phone", user.getPhone(),
                        "name", user.getName(),
                        "surnamePaternal", user.getSurnamePaternal(),
                        "surnameMaternal", user.getSurnameMaternal()
                  );
                  String body = objectMapper.writeValueAsString(event);
                  kafkaTemplate.send(teacherRegisterTopic, body);

                  log.info("Evento de registro de profesor enviado para el usuario: {}", user.getAccess().getUsername());
            } catch (Exception e) {
                  log.error("Fallo al enviar el evento de registro de profesor: {}", e.getMessage());
            }
      }

}
