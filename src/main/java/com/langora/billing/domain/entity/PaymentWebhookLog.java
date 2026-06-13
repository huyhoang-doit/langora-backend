package com.langora.billing.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.billing.domain.enums.PaymentProvider;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "payment_webhook_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentWebhookLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    PaymentProvider provider;

    String paymentId;

    String eventName;

    String payload;

    Boolean processed;

    java.time.OffsetDateTime processedAt;

    java.time.OffsetDateTime createdAt;
}
