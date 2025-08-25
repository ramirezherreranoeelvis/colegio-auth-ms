package com.authms.infrastructure.output.notification.dto; // O donde prefieras

import lombok.Builder;

@Builder
public record TeacherRegisteredEvent(
      String id,
      String phone,
      String name,
      String surnamePaternal,
      String surnameMaternal
) {}