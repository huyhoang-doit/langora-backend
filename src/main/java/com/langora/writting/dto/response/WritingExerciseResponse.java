package com.langora.writting.dto.response;

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
public class WritingExerciseResponse {
    String id;
    String languageId;
    String levelId;
    String levelName;
    String contentTypeId;
    String contentTypeName;
    String topicId;
    String topicName;
    String title;
    String summary;
    String thumbnailUrl;
    Integer totalSentences;
    Integer estimatedMinutes;
    Integer creditsReward;
    Integer xpReward;
    Boolean isActive;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
