package com.langora.identity.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.identity.domain.enums.SessionStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String deviceId;

    String deviceName;

    String operatingSystem;

    String browser;

    String ipAddress;

    String userAgent;

    @Enumerated(EnumType.STRING)
    SessionStatus status;

    java.time.OffsetDateTime lastActivityAt;

    java.time.OffsetDateTime expiredAt;

    java.time.OffsetDateTime createdAt;
}
