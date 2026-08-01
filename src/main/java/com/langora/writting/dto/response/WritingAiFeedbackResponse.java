package com.langora.writting.dto.response;

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
public class WritingAiFeedbackResponse {

    String id;
    String sessionId;
    String sentenceId;
    String overallFeedback;
    String grammarFeedback;
    String vocabularyFeedback;
    String fluencyFeedback;
}
