package com.langora.billing.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "credit_expirations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditExpiration {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String walletId;

    Integer credits;

    java.time.OffsetDateTime expiresAt;

    java.time.OffsetDateTime expiredAt;

    Boolean isExpired;

    java.time.OffsetDateTime createdAt;
}
