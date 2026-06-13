package com.langora.learning.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_lesson_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String lessonId;

    java.math.BigDecimal progressPercentage;

    Boolean completed;

    java.time.OffsetDateTime completedAt;

    java.time.OffsetDateTime lastAccessedAt;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
