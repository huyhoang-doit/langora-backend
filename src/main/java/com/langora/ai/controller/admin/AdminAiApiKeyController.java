package com.langora.ai.controller.admin;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.langora.ai.dto.request.AiApiKeyRequest;
import com.langora.ai.dto.request.AiApiKeyStatusRequest;
import com.langora.ai.dto.response.AiApiKeyResponse;
import com.langora.ai.service.AiApiKeyService;
import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.shared.dto.response.PageMeta;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Admin.Ai.Keys.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// @PreAuthorize("hasRole('ADMIN')")
public class AdminAiApiKeyController {

    AiApiKeyService aiApiKeyService;

    @GetMapping
    public ApiResponse<List<AiApiKeyResponse>> getApiKeys(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        Page<AiApiKeyResponse> pageData = aiApiKeyService.getApiKeys(search, page, limit);
        PageMeta pageMeta = PageMeta.builder()
                .page(page)
                .limit(limit)
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .build();

        return ApiResponse.<List<AiApiKeyResponse>>builder()
                .data(pageData.getContent())
                .meta(pageMeta)
                .message("Fetched AI API Keys successfully")
                .build();
    }

    @PostMapping
    public ApiResponse<AiApiKeyResponse> createApiKey(@RequestBody @Valid AiApiKeyRequest request) {
        AiApiKeyResponse response = aiApiKeyService.createApiKey(request);
        return ApiResponse.<AiApiKeyResponse>builder()
                .data(response)
                .message("Created AI API Key successfully")
                .build();
    }

    @PostMapping(ApiEndpoint.Admin.Ai.Keys.BULK)
    public ApiResponse<Void> bulkImportApiKeys(@RequestBody java.util.List<AiApiKeyRequest> requests) {
        aiApiKeyService.bulkImportApiKeys(requests);
        return ApiResponse.<Void>builder()
                .message("Bulk import successful")
                .build();
    }

    @PutMapping(ApiEndpoint.Admin.Ai.Keys.ID)
    public ApiResponse<AiApiKeyResponse> updateApiKey(
            @PathVariable String id, @RequestBody @Valid AiApiKeyRequest request) {
        AiApiKeyResponse response = aiApiKeyService.updateApiKey(id, request);
        return ApiResponse.<AiApiKeyResponse>builder()
                .data(response)
                .message("Updated AI API Key successfully")
                .build();
    }

    @PatchMapping(ApiEndpoint.Admin.Ai.Keys.STATUS)
    public ApiResponse<AiApiKeyResponse> updateStatus(
            @PathVariable String id, @RequestBody @Valid AiApiKeyStatusRequest request) {
        AiApiKeyResponse response = aiApiKeyService.updateStatus(id, request);
        return ApiResponse.<AiApiKeyResponse>builder()
                .data(response)
                .message("Updated AI API Key status successfully")
                .build();
    }

    @DeleteMapping(ApiEndpoint.Admin.Ai.Keys.ID)
    public ApiResponse<Void> deleteApiKey(@PathVariable String id) {
        aiApiKeyService.deleteApiKey(id);
        return ApiResponse.<Void>builder()
                .message("Deleted AI API Key successfully")
                .build();
    }
}
