package com.authms.infrastructure.output.notification.dto; // O donde prefieras

import lombok.Builder;

// Un 'record' es una clase inmutable ideal para DTOs.
@Builder
public record StudentRegisteredEvent(
      String id,
      String name,
      String surnamePaternal,
      String surnameMaternal,
      Integer fatherDni,
      Integer motherDni,
      Integer representativeDni
) {}