package com.authms.infrastructure.config;

import org.springframework.stereotype.Component;

@Component
public class Logger {
      private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(getClass().getName());
      public void log(String message){
            logger.info(message);
      }
}
