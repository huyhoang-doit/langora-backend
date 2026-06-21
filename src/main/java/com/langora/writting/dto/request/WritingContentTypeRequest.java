package com.langora.writting.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
public class WritingContentTypeRequest {

    @NotNull(message = "Code is required")
    WritingContentTypeCode code;

    @NotBlank(message = "Name is required")
    String name;

    String iconUrl;

    String description;

    @NotNull(message = "Display order is required")
    Integer displayOrder;
}
