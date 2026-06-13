package com.langora.learning.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "learning_paths")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LearningPath {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String languageId;

    String title;

    String description;

    Integer estimatedHours;

    Boolean isDefault;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
