package com.authms.infrastructure.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponseEntity<T> {

      private String message;
      private String status;
      private T data;

}
