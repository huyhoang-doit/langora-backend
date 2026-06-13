package com.langora.learning.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_unit_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUnitProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String unitId;

    Integer completedLessons;

    Integer totalLessons;

    java.math.BigDecimal completionPercentage;

    Boolean completed;

    java.time.OffsetDateTime completedAt;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
