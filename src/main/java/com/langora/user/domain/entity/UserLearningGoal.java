package com.langora.user.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_learning_goals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLearningGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String targetLanguageId;

    String goalTitle;

    Integer targetWords;

    Integer targetLessons;

    Integer targetDays;

    java.time.LocalDate startDate;

    java.time.LocalDate endDate;

    Boolean completed;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
