package com.langora.identity.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.identity.domain.enums.AuditAction;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "security_audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    @Enumerated(EnumType.STRING)
    AuditAction action;

    String entityName;

    String entityId;

    String oldValue;

    String newValue;

    String ipAddress;

    java.time.OffsetDateTime createdAt;
}
