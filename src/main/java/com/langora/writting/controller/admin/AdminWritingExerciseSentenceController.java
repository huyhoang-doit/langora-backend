package com.langora.writting.controller.admin;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.writting.dto.request.WritingExerciseSentenceRequest;
import com.langora.writting.dto.response.WritingExerciseSentenceResponse;
import com.langora.writting.service.WritingExerciseSentenceService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Admin.WritingExerciseSentences.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminWritingExerciseSentenceController {

    WritingExerciseSentenceService writingExerciseSentenceService;

    @GetMapping(ApiEndpoint.Admin.WritingExerciseSentences.ID)
    public ApiResponse<WritingExerciseSentenceResponse> getSentence(@PathVariable String id) {
        WritingExerciseSentenceResponse result = writingExerciseSentenceService.getSentence(id);
        return ApiResponse.<WritingExerciseSentenceResponse>builder()
                .data(result)
                .build();
    }

    @PutMapping(ApiEndpoint.Admin.WritingExerciseSentences.ID)
    public ApiResponse<WritingExerciseSentenceResponse> updateSentence(
            @PathVariable String id, @RequestBody @Valid WritingExerciseSentenceRequest request) {
        WritingExerciseSentenceResponse result = writingExerciseSentenceService.updateSentence(id, request);
        return ApiResponse.<WritingExerciseSentenceResponse>builder()
                .data(result)
                .build();
    }

    @DeleteMapping(ApiEndpoint.Admin.WritingExerciseSentences.ID)
    public ApiResponse<Void> deleteSentence(@PathVariable String id) {
        writingExerciseSentenceService.deleteSentence(id);
        return ApiResponse.<Void>builder()
                .message("Deleted writing exercise sentence successfully")
                .build();
    }
}
