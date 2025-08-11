package com.authms.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;

import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG;

@RequiredArgsConstructor
@Configuration
public class KafkaProducerConfig {

      private final KafkaProperties kafkaProperties;
      @Value("welcome-topic")
      private String welcomeTopic;
      @Value("student-register-topic")
      private String studentRegisterTopic;

      @Bean
      public ProducerFactory<String, String> producerFactory() {
            var configs = new HashMap<String, Object>();
            configs.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
            configs.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configs.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configs.put(ACKS_CONFIG, "all");
            configs.put(RETRIES_CONFIG, 3);
            return new DefaultKafkaProducerFactory<>(configs);
      }

      @Bean
      public KafkaTemplate<String, String> kafkaTemplate() {
            return new KafkaTemplate<>(producerFactory());
      }

      @Bean
      public String welcomeTopic() {
            return welcomeTopic;
      }

      @Bean
      public String studentRegisterTopic() {
            return studentRegisterTopic;
      }

}
