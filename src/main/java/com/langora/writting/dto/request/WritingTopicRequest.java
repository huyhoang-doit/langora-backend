package com.langora.writting.dto.request;

import jakarta.validation.constraints.NotBlank;

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
public class WritingTopicRequest {

    @NotBlank(message = "Code is required")
    String code;

    @NotBlank(message = "Name is required")
    String name;

    String description;

    Integer displayOrder;
}
