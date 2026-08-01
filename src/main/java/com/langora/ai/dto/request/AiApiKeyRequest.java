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
public class AiApiKeyRequest {

    @NotBlank(message = "Provider is required")
    String provider;

    String rawKey;

    String usage;

    String rank;

    @NotNull(message = "Active status is required")
    Boolean active;
}
