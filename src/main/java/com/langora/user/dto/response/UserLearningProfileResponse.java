package com.langora.user.dto.response;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.langora.user.domain.enums.LearningGoalType;
import com.langora.user.domain.enums.ProficiencyLevel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLearningProfileResponse {
    String id;
    String targetLanguageId;
    String currentLevelId;
    LearningGoalType learningGoal;
    String targetExam;
    Integer dailyGoalMinutes;
    Integer dailyGoalWords;
    ProficiencyLevel proficiency;
    LocalDate startDate;
    Boolean isActive;
    OffsetDateTime updatedAt;
}
