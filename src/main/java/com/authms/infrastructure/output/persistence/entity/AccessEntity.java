package com.authms.infrastructure.output.persistence.entity;

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
@Table("access")
public class AccessEntity extends AuditableEntity implements Persistable<String> {

      @Id
      private String id;

      @Column("access_enabled")
      private Boolean accessEnabled;

      @Column("username")
      private String username;

      @Column("password")
      private String password;

      @Column("description")
      private String description;

      @Transient
      private boolean isNew;

      @Override
      public boolean isNew() {
            return this.isNew || this.id == null;
      }

      public void markNew() {
            this.isNew = true;
      }

      public static AccessEntity newAccess() {
            AccessEntity access = new AccessEntity();
            access.setId(UUID.randomUUID().toString());
            access.markNew();
            return access;
      }

}
