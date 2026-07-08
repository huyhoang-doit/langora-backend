package com.langora.ai.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AiPromptRequest {

    @NotBlank(message = "Name is required")
    String name;

    @NotBlank(message = "Field is required")
    String field;

    @NotBlank(message = "System prompt is required")
    String systemPrompt;

    @NotBlank(message = "API Key ID is required")
    String apiKeyId;

    @NotNull(message = "Active status is required")
    Boolean active;
}
