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
public class WritingExerciseSentenceResponse {

    String id;

    String exerciseId;

    Integer sentenceOrder;

    String sourceText;

    String targetText;

    java.util.List<String> vocabularyHints;

    java.util.List<String> grammarHints;

    java.math.BigDecimal difficultyScore;

    OffsetDateTime createdAt;

    OffsetDateTime updatedAt;
}
