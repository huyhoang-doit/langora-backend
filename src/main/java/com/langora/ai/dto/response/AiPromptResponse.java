package com.langora.ai.dto.response;

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
public class AiPromptResponse {

    String id;
    String name;
    String field;
    String systemPrompt;
    String apiKeyId;
    Boolean active;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
