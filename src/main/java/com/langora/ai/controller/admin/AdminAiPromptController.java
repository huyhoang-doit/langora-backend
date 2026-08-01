package com.langora.ai.controller.admin;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.ai.dto.request.AiPromptRequest;
import com.langora.ai.dto.request.AiPromptStatusRequest;
import com.langora.ai.dto.response.AiPromptResponse;
import com.langora.ai.service.AiPromptService;
import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Admin.Ai.Prompts.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// @PreAuthorize("hasRole('ADMIN')")
public class AdminAiPromptController {

    AiPromptService aiPromptService;

    @GetMapping
    public ApiResponse<List<AiPromptResponse>> getPrompts() {
        List<AiPromptResponse> responses = aiPromptService.getPrompts();
        return ApiResponse.<List<AiPromptResponse>>builder()
                .data(responses)
                .message("Fetched AI Prompts successfully")
                .build();
    }

    @PostMapping
    public ApiResponse<AiPromptResponse> createPrompt(@RequestBody @Valid AiPromptRequest request) {
        AiPromptResponse response = aiPromptService.createPrompt(request);
        return ApiResponse.<AiPromptResponse>builder()
                .data(response)
                .message("Created AI Prompt successfully")
                .build();
    }

    @PostMapping(ApiEndpoint.Admin.Ai.Prompts.BULK)
    public ApiResponse<Void> bulkImportPrompts(@RequestBody List<AiPromptRequest> requests) {
        aiPromptService.bulkImportPrompts(requests);
        return ApiResponse.<Void>builder().message("Bulk import successful").build();
    }

    @PutMapping(ApiEndpoint.Admin.Ai.Prompts.ID)
    public ApiResponse<AiPromptResponse> updatePrompt(
            @PathVariable String id, @RequestBody @Valid AiPromptRequest request) {
        AiPromptResponse response = aiPromptService.updatePrompt(id, request);
        return ApiResponse.<AiPromptResponse>builder()
                .data(response)
                .message("Updated AI Prompt successfully")
                .build();
    }

    @PatchMapping(ApiEndpoint.Admin.Ai.Prompts.STATUS)
    public ApiResponse<AiPromptResponse> updateStatus(
            @PathVariable String id, @RequestBody @Valid AiPromptStatusRequest request) {
        AiPromptResponse response = aiPromptService.updateStatus(id, request);
        return ApiResponse.<AiPromptResponse>builder()
                .data(response)
                .message("Updated AI Prompt status successfully")
                .build();
    }
}
