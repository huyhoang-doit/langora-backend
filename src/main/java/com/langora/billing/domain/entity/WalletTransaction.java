package com.langora.billing.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.billing.domain.enums.WalletTransactionType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "wallet_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String walletId;

    String paymentId;

    @Enumerated(EnumType.STRING)
    WalletTransactionType transactionType;

    Integer credits;

    Long balanceBefore;

    Long balanceAfter;

    String description;

    String metadata;

    java.time.OffsetDateTime createdAt;
}
