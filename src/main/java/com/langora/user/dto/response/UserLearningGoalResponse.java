package com.langora.user.dto.response;

import java.time.LocalDate;
import java.time.OffsetDateTime;

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
public class UserLearningGoalResponse {
    String id;
    String targetLanguageId;
    String goalTitle;
    Integer targetWords;
    Integer targetLessons;
    Integer targetDays;
    LocalDate startDate;
    LocalDate endDate;
    Boolean completed;
    OffsetDateTime updatedAt;
}
