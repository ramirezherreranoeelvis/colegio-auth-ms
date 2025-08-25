package com.authms.infrastructure.output.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentNotificationProducer {

      private String id;
      private String name;
      private String surnamePaternal;
      private String surnameMaternal;
      private Integer fatherDni;
      private Integer motherDni;
      private Integer representativeDni;

}
