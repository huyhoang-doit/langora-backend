package com.langora.user.dto.response;

import java.time.LocalDate;

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
public class UserProgressResponse {
    String id;
    String languageId;
    String currentLevelId;
    Integer totalLearnedWords;
    Integer totalMasteredWords;
    Integer totalLessonsCompleted;
    Integer totalStudyMinutes;
    Integer currentStreak;
    Integer longestStreak;
    LocalDate lastLearningDate;
}
