package com.langora.writting.controller.client;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.shared.utils.SecurityUtils;
import com.langora.writting.dto.request.WritingBulkSentenceAnswerRequest;
import com.langora.writting.dto.request.WritingSentenceAnswerRequest;
import com.langora.writting.dto.request.WritingSessionCreateRequest;
import com.langora.writting.dto.request.WritingSessionStatusUpdateRequest;
import com.langora.writting.dto.response.WritingAiFeedbackResponse;
import com.langora.writting.dto.response.WritingSessionResponse;
import com.langora.writting.service.WritingSessionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.WritingSessions.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientWritingSessionController {

    WritingSessionService writingSessionService;

    @PostMapping
    public ApiResponse<WritingSessionResponse> createSession(@RequestBody @Valid WritingSessionCreateRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        WritingSessionResponse response = writingSessionService.createSession(userId, request);
        return ApiResponse.<WritingSessionResponse>builder()
                .data(response)
                .message("Session created successfully")
                .build();
    }

    @GetMapping(ApiEndpoint.Client.WritingSessions.ID)
    public ApiResponse<WritingSessionResponse> getSessionById(@PathVariable String id) {
        WritingSessionResponse response = writingSessionService.getSession(id);
        return ApiResponse.<WritingSessionResponse>builder()
                .data(response)
                .message("Fetched session successfully")
                .build();
    }

    @PutMapping(ApiEndpoint.Client.WritingSessions.ID)
    public ApiResponse<WritingSessionResponse> updateSessionStatus(
            @PathVariable String id, @RequestBody @Valid WritingSessionStatusUpdateRequest request) {
        WritingSessionResponse response = writingSessionService.updateSessionStatus(id, request);
        return ApiResponse.<WritingSessionResponse>builder()
                .data(response)
                .message("Session updated successfully")
                .build();
    }

    @PostMapping(ApiEndpoint.Client.WritingSessions.SUBMIT)
    public ApiResponse<WritingSessionResponse> submitSession(@PathVariable String id) {
        WritingSessionResponse response = writingSessionService.submitSession(id);
        return ApiResponse.<WritingSessionResponse>builder()
                .data(response)
                .message("Session submitted successfully")
                .build();
    }

    @PostMapping(ApiEndpoint.Client.WritingSessions.SENTENCE_ANSWERS)
    public ApiResponse<WritingAiFeedbackResponse> submitSentenceAnswer(
            @PathVariable String id, @RequestBody @Valid WritingSentenceAnswerRequest request) {
        WritingAiFeedbackResponse feedback = writingSessionService.submitSentenceAnswer(id, request);
        return ApiResponse.<WritingAiFeedbackResponse>builder()
                .data(feedback)
                .message("Answer submitted successfully")
                .build();
    }

    @PostMapping(ApiEndpoint.Client.WritingSessions.BULK_SENTENCE_ANSWERS)
    public ApiResponse<WritingSessionResponse> submitBulkSentenceAnswers(
            @PathVariable String id, @RequestBody @Valid WritingBulkSentenceAnswerRequest request) {
        WritingSessionResponse response = writingSessionService.submitBulkSentenceAnswers(id, request);
        return ApiResponse.<WritingSessionResponse>builder()
                .data(response)
                .message("Bulk answers submitted successfully")
                .build();
    }

    @GetMapping(ApiEndpoint.Client.WritingSessions.AI_FEEDBACKS)
    public ApiResponse<List<WritingAiFeedbackResponse>> getAiFeedbacks(@PathVariable String id) {
        List<WritingAiFeedbackResponse> responses = writingSessionService.getAiFeedbacks(id);
        return ApiResponse.<List<WritingAiFeedbackResponse>>builder()
                .data(responses)
                .message("Fetched AI feedbacks successfully")
                .build();
    }
}
