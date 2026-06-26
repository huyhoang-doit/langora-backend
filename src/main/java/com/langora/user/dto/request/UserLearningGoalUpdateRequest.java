package com.langora.user.dto.request;

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
public class UserLearningGoalUpdateRequest {
    String targetLanguageId;
    String goalTitle;
    Integer targetWords;
    Integer targetLessons;
    Integer targetDays;
    LocalDate startDate;
    LocalDate endDate;
}
