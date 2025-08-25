package com.authms.domain;

import com.authms.domain.enums.RolUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable {

      private String id;
      private Integer dni;
      private String name;
      private String surnamePaternal;
      private String surnameMaternal;
      private String phone;
      private Access access;
      private RolUser rol;
      private String fatherId;
      private String motherId;
      private String representativeId;

}
