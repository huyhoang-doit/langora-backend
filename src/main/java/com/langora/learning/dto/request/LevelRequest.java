package com.langora.learning.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
public class LevelRequest {

    @NotBlank(message = "Code is required")
    String code;

    @NotBlank(message = "Name is required")
    String name;

    @NotNull(message = "Order index is required")
    Integer orderIndex;

    String description;
}
