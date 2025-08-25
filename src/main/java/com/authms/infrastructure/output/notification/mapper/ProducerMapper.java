package com.authms.infrastructure.output.notification.mapper;

import com.authms.domain.Token;
import com.authms.domain.User;
import com.authms.infrastructure.output.notification.dto.StudentRegisteredEvent;
import com.authms.infrastructure.output.notification.dto.TeacherRegisteredEvent;
import com.authms.infrastructure.output.notification.dto.UserWelcome;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class ProducerMapper {

      public UserWelcome mapToProducer(User user, Token token) {
            return UserWelcome.builder()
                  .token(token.getValue())
                  .expiryDate(token.getExpiryDate())
                  .id(user.getId())
                  .username(user.getAccess().getUsername())
                  .dni(user.getDni())
                  .rol(user.getRol().toString())
                  .name(user.getName())
                  .surnamePaternal(user.getSurnamePaternal())
                  .surnameMaternal(user.getSurnameMaternal())
                  .build();
      }

      public StudentRegisteredEvent mapToProducer(User student, Integer fatherDni, Integer motherDni, Integer representativeDni) {
            return StudentRegisteredEvent.builder()
                  .id(student.getId())
                  .name(student.getName())
                  .surnamePaternal(student.getSurnamePaternal())
                  .surnameMaternal(student.getSurnameMaternal())
                  .fatherDni(fatherDni)
                  .motherDni(motherDni)
                  .representativeDni(representativeDni)
                  .build();
      }

      public TeacherRegisteredEvent mapToProducer(User teacher) {
            return TeacherRegisteredEvent.builder()
                  .id(teacher.getId())
                  .phone(teacher.getPhone())
                  .name(teacher.getName())
                  .surnamePaternal(teacher.getSurnamePaternal())
                  .surnameMaternal(teacher.getSurnameMaternal())
                  .build();
      }

}
