package com.langora.writting.controller.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.writting.dto.response.WritingExerciseResponse;
import com.langora.writting.service.WritingExerciseService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.WritingExercises.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientWritingExerciseController {

    WritingExerciseService writingExerciseService;

    @GetMapping
    public ApiResponse<List<WritingExerciseResponse>> getExercises(
            @PathVariable String languageId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String levelId,
            @RequestParam(required = false) String topicId,
            @RequestParam(required = false) String contentTypeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "100") int size) {

        // Actually languageId is technically required in DB filter if topicId/levelId are not present,
        // but for now we pass whatever the client gives (typically they pass levelId and topicId).
        // Since getExercises needs langId, we assume client provides it or we rely on level/topic.
        // Wait, WritingExerciseService requires langId. So we must pass it.
        // We will pass languageId from query param if available, otherwise it throws exception.
        Page<WritingExerciseResponse> exercisePage =
                writingExerciseService.getExercises(languageId, search, levelId, topicId, contentTypeId, page, size);

        return ApiResponse.<List<WritingExerciseResponse>>builder()
                .data(exercisePage.getContent())
                .message("Fetched exercises successfully")
                .build();
    }

    @GetMapping(ApiEndpoint.Client.WritingExercises.ID)
    public ApiResponse<WritingExerciseResponse> getExerciseById(
            @PathVariable String languageId, @PathVariable String id) {
        WritingExerciseResponse response = writingExerciseService.getExercise(id);
        return ApiResponse.<WritingExerciseResponse>builder()
                .data(response)
                .message("Fetched exercise successfully")
                .build();
    }
}
