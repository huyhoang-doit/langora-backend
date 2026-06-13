package com.langora.billing.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.billing.domain.enums.CreditActionType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "credit_ledgers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String walletId;

    @Enumerated(EnumType.STRING)
    CreditActionType actionType;

    String referenceId;

    Integer creditsChange;

    Long balanceBefore;

    Long balanceAfter;

    String metadata;

    java.time.OffsetDateTime createdAt;
}
