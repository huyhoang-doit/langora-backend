package com.langora.identity.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String roleId;

    java.time.OffsetDateTime assignedAt;

    String assignedBy;
}
