package com.langora.writting.controller.admin;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.shared.dto.response.PageMeta;
import com.langora.writting.dto.request.WritingExerciseRequest;
import com.langora.writting.dto.request.WritingExerciseStatusUpdateRequest;
import com.langora.writting.dto.response.WritingExerciseResponse;
import com.langora.writting.service.WritingExerciseService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Admin.WritingExercises.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminWritingExerciseController {

    WritingExerciseService writingExerciseService;

    @GetMapping
    public ApiResponse<java.util.List<WritingExerciseResponse>> getExercises(
            @RequestParam String languageId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String levelId,
            @RequestParam(required = false) String topicId,
            @RequestParam(required = false) String contentTypeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<WritingExerciseResponse> exercisePage =
                writingExerciseService.getExercises(languageId, search, levelId, topicId, contentTypeId, page, size);

        PageMeta meta = PageMeta.builder()
                .page(page)
                .limit(size)
                .totalElements(exercisePage.getTotalElements())
                .totalPages(exercisePage.getTotalPages())
                .build();

        return ApiResponse.<java.util.List<WritingExerciseResponse>>builder()
                .data(exercisePage.getContent())
                .meta(meta)
                .build();
    }

    @GetMapping(ApiEndpoint.Admin.WritingExercises.ID)
    public ApiResponse<WritingExerciseResponse> getExercise(@PathVariable String id) {
        WritingExerciseResponse result = writingExerciseService.getExercise(id);
        return ApiResponse.<WritingExerciseResponse>builder().data(result).build();
    }

    @PostMapping
    public ApiResponse<WritingExerciseResponse> createExercise(@RequestBody @Valid WritingExerciseRequest request) {
        WritingExerciseResponse result = writingExerciseService.createExercise(request);
        return ApiResponse.<WritingExerciseResponse>builder().data(result).build();
    }

    @PutMapping(ApiEndpoint.Admin.WritingExercises.ID)
    public ApiResponse<WritingExerciseResponse> updateExercise(
            @PathVariable String id, @RequestBody @Valid WritingExerciseRequest request) {
        WritingExerciseResponse result = writingExerciseService.updateExercise(id, request);
        return ApiResponse.<WritingExerciseResponse>builder().data(result).build();
    }

    @PatchMapping(ApiEndpoint.Admin.WritingExercises.STATUS)
    public ApiResponse<WritingExerciseResponse> updateStatus(
            @PathVariable String id, @RequestBody @Valid WritingExerciseStatusUpdateRequest request) {
        WritingExerciseResponse result = writingExerciseService.updateStatus(id, request);
        return ApiResponse.<WritingExerciseResponse>builder().data(result).build();
    }

    @DeleteMapping(ApiEndpoint.Admin.WritingExercises.ID)
    public ApiResponse<Void> deleteExercise(@PathVariable String id) {
        writingExerciseService.deleteExercise(id);
        return ApiResponse.<Void>builder().message("Deleted successfully").build();
    }

    @PostMapping(ApiEndpoint.Admin.WritingExercises.IMPORT)
    public ApiResponse<Void> bulkImportExercises(
            @PathVariable("languageId") String languageId,
            @RequestBody java.util.List<WritingExerciseRequest> requests) {
        writingExerciseService.bulkImportExercises(languageId, requests);
        return ApiResponse.<Void>builder().message("Bulk import successful").build();
    }
}
