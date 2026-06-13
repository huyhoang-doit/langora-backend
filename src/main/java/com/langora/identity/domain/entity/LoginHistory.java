package com.langora.identity.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "login_histories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String sessionId;

    String ipAddress;

    String userAgent;

    Boolean success;

    String failureReason;

    java.time.OffsetDateTime loggedAt;
}
