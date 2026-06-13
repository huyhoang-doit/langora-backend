package com.langora.identity.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.identity.domain.enums.UserStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String email;

    String passwordHash;

    @Enumerated(EnumType.STRING)
    UserStatus status;

    Boolean emailVerified;

    java.time.OffsetDateTime lastLoginAt;

    java.time.OffsetDateTime createdAt;

    String createdBy;

    java.time.OffsetDateTime updatedAt;

    String updatedBy;

    java.time.OffsetDateTime deletedAt;

    String deletedBy;
}
