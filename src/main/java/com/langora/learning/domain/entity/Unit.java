package com.langora.learning.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "units")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String learningPathId;

    String levelId;

    String title;

    String description;

    Integer orderIndex;

    Integer estimatedMinutes;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
