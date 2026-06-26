package com.langora.user.dto.request;

import com.langora.user.domain.enums.LearningGoalType;

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
public class UserLearningProfileUpdateRequest {
    String targetLanguageId;
    String currentLevelId;
    LearningGoalType learningGoal;
    String targetExam;
    Integer dailyGoalMinutes;
    Integer dailyGoalWords;
}
