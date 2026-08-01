package com.langora.ai.dto.response;

import java.math.BigDecimal;

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
public class AiFeedbackResultDto {
    BigDecimal aiScore;
    BigDecimal grammarScore;
    BigDecimal vocabularyScore;
    BigDecimal fluencyScore;
    BigDecimal accuracyScore;
    String overallFeedback;
    String grammarFeedback;
    String vocabularyFeedback;
    String fluencyFeedback;
}
