package com.langora.user.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_language_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLanguageProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String languageId;

    String currentLevelId;

    Integer totalLearnedWords;

    Integer totalMasteredWords;

    Integer totalLessonsCompleted;

    Integer totalStudyMinutes;

    Integer currentStreak;

    Integer longestStreak;

    java.time.LocalDate lastLearningDate;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
