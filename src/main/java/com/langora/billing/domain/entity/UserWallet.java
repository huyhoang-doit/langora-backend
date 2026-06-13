package com.langora.billing.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    Long availableCredits;

    Long totalTopupCredits;

    Long totalRewardCredits;

    Long totalSpentCredits;

    Long totalRefundedCredits;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
