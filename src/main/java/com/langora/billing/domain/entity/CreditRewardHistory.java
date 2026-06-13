package com.langora.billing.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "credit_reward_histories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditRewardHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String walletId;

    String rewardRuleId;

    Integer creditsReward;

    String referenceId;

    java.time.OffsetDateTime awardedAt;
}
