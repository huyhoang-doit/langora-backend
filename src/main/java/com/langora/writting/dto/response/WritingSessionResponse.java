package com.langora.writting.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingSessionResponse {

    String id;
    String userId;
    String exerciseId;
    String status;
    Integer currentSentenceOrder;
    BigDecimal totalScore;
    BigDecimal grammarScore;
    BigDecimal vocabularyScore;
    BigDecimal fluencyScore;
    BigDecimal accuracyScore;
    OffsetDateTime startedAt;
    OffsetDateTime submittedAt;
    OffsetDateTime completedAt;
}
