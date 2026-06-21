package com.langora.writting.dto.response;

import java.time.OffsetDateTime;

import com.langora.writting.domain.enums.WritingContentTypeCode;

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
public class WritingContentTypeResponse {
    String id;
    String languageId;
    WritingContentTypeCode code;
    String name;
    String iconUrl;
    String description;
    Integer displayOrder;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
}
