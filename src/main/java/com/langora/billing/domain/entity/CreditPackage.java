package com.langora.billing.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "credit_packages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String code;

    String name;

    String description;

    Integer credits;

    Integer bonusCredits;

    java.math.BigDecimal price;

    String currency;

    Integer sortOrder;

    Boolean isActive;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
