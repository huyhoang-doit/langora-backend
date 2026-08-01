package com.langora.writting.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

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
public class WritingBulkSentenceAnswerRequest {

    @NotEmpty(message = "Answers list cannot be empty")
    @Valid
    List<WritingSentenceAnswerRequest> answers;

    @Builder.Default
    boolean submitSession = false;
}
