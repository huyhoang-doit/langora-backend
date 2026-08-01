package com.langora.learning.dto.response;

import java.time.OffsetDateTime;

import com.langora.learning.domain.enums.LanguageStatus;

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
public class LanguageResponse {
    String id;
    String code;
    String name;
    String nativeName;
    String flagIconUrl;
    LanguageStatus status;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
