package com.langora.identity.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "role_permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String roleId;

    String permissionId;

    java.time.OffsetDateTime grantedAt;
}
