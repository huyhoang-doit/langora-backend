package com.langora.billing.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.billing.domain.enums.CreditActionType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "credit_consumption_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditConsumptionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    CreditActionType actionType;

    Integer creditsCost;

    Boolean isActive;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
