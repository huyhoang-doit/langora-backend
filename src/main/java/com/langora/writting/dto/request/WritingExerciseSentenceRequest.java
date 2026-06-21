package com.langora.writting.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingExerciseSentenceRequest {

    @NotNull(message = "Thứ tự câu không được để trống")
    Integer sentenceOrder;

    @NotBlank(message = "Câu gốc không được để trống")
    String sourceText;

    @NotBlank(message = "Câu dịch mẫu không được để trống")
    String targetText;

    List<String> vocabularyHints;

    List<String> grammarHints;

    BigDecimal difficultyScore;
}
