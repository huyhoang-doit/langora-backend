package com.langora.writting.dto.request;

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
public class WritingExerciseRequest {

    @NotBlank(message = "Language ID is required")
    String languageId;

    @NotBlank(message = "Level ID is required")
    String levelId;

    @NotBlank(message = "Content Type ID is required")
    String contentTypeId;

    @NotBlank(message = "Topic ID is required")
    String topicId;

    @NotBlank(message = "Title is required")
    String title;

    String summary;

    String thumbnailUrl;

    Integer estimatedMinutes;

    Integer creditsReward;

    Integer xpReward;

    @NotNull(message = "Status is required")
    Boolean isActive;
}
