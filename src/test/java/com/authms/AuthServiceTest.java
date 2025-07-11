package com.authms;

import com.authms.application.port.input.LoginUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

@SpringBootTest
class AuthServiceTest {

      @Autowired
      private LoginUseCase loginUseCase;

      @Test
      void login() {
            this.loginUseCase.login("fcoricu", "10002000");
      }

}
