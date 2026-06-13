package com.langora.user.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.user.domain.enums.LearningGoalType;
import com.langora.user.domain.enums.ProficiencyLevel;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_learning_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLearningProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String targetLanguageId;

    String currentLevelId;

    @Enumerated(EnumType.STRING)
    LearningGoalType learningGoal;

    String targetExam;

    Integer dailyGoalMinutes;

    Integer dailyGoalWords;

    @Enumerated(EnumType.STRING)
    ProficiencyLevel proficiency;

    java.time.LocalDate startDate;

    Boolean isActive;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
