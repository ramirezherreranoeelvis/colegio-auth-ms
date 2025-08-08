package com.authms.infrastructure.output.security;

import com.authms.application.port.output.IPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncoder implements IPasswordEncoder {

      private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

      @Override
      public String encode(String rawPassword) {
            return passwordEncoder.encode(rawPassword);
      }

      @Override
      public boolean matches(String rawPassword, String encodedPassword) {
            return passwordEncoder.matches(rawPassword, encodedPassword);
      }

}
