package com.langora.identity.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String sessionId;

    String tokenHash;

    Boolean revoked;

    java.time.OffsetDateTime revokedAt;

    java.time.OffsetDateTime expiresAt;

    java.time.OffsetDateTime createdAt;
}
