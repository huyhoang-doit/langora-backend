package com.langora.billing.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "credit_reward_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditRewardRule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String rewardCode;

    String rewardName;

    Integer creditsReward;

    Integer dailyLimit;

    Boolean isActive;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
