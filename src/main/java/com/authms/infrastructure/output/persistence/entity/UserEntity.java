package com.authms.infrastructure.output.persistence.entity;

import com.authms.infrastructure.output.persistence.enums.RolUser;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity extends AuditableEntity implements Persistable<String> {

      @Id
      private String id;

      @Column("dni")
      private String dni;

      @Column("name")
      private String name;

      @Column("surname_paternal")
      private String surnamePaternal;

      @Column("surname_maternal")
      private String surnameMaternal;

      @Column("phone")
      private String phone;

      @Column("id_access")
      private String idAccess;

      @Column("rol")
      private RolUser rol;

      @Column("id_father")
      private String idFather;

      @Column("id_mother")
      private String idMother;

      @Column("id_representative")
      private String idRepresentative;

      @Transient
      private boolean isNew;

      @Override
      public boolean isNew() {
            return isNew || id == null;
      }

      public void markNew() {
            this.isNew = true;
      }

      public static UserEntity newUser() {
            UserEntity user = new UserEntity();
            user.setId(UUID.randomUUID().toString());
            user.markNew();
            return user;
      }

}
