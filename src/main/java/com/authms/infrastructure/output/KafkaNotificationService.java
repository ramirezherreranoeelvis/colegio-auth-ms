package com.authms.infrastructure.output;

import com.authms.domain.Token;
import com.authms.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class KafkaNotificationService {

      private final KafkaTemplate<String, String> kafkaTemplate;
      private final ObjectMapper objectMapper;
      private final String welcomeTopic;

      public void sendWelcome(User user, Token token) {
            try {
                  Map<String, String> event = Map.of(
                        "token", token.getValue(),
                        "expiryDate", token.getExpiryDate().toString(),
                        "username", user.getAccess().getUsername(),
                        "dni", user.getDni(),
                        "rol", user.getRol().name(),
                        "name", user.getName(),
                        "surnamePaternal", user.getSurnamePaternal(),
                        "surnameMaternal", user.getSurnameMaternal()
                  );
                  String body = objectMapper.writeValueAsString(event);
                  kafkaTemplate.send(welcomeTopic, body);

                  log.info("Welcome email event sent for user: {}", user.getAccess().getUsername());
            } catch (Exception e) {
                  log.error("Failed to send welcome email event: {}", e.getMessage());
            }
      }

}
