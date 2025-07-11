package com.authms.application.port.output;

import org.springframework.stereotype.Component;

public interface IPasswordEncoder {

      String encode(String rawPassword);

      boolean matches(String rawPassword, String encodedPassword);
}
