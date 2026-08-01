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
public class AiApiKeyResponse {

    String id;
    String provider;
    String mask;
    String usage;
    String rank;
    Boolean active;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
