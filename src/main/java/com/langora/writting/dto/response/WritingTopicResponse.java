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
public class WritingTopicResponse {
    String id;
    String languageId;
    String code;
    String name;
    String description;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
