package com.langora.billing.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.billing.domain.enums.PaymentProvider;
import com.langora.billing.domain.enums.PaymentStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String packageId;

    @Enumerated(EnumType.STRING)
    PaymentProvider provider;

    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    java.math.BigDecimal amount;

    String currency;

    String providerTransactionId;

    String paymentUrl;

    String providerResponse;

    java.time.OffsetDateTime paidAt;

    java.time.OffsetDateTime expiredAt;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
