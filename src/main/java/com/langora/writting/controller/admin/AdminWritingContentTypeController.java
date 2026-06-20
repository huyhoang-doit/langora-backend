package com.langora.writting.controller.admin;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.langora.writting.dto.request.WritingContentTypeRequest;
import com.langora.writting.dto.response.WritingContentTypeResponse;
import com.langora.writting.service.WritingContentTypeService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Admin.Languages.BASE + ApiEndpoint.Admin.Languages.WRITING_CONTENT_TYPES)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminWritingContentTypeController {

    WritingContentTypeService writingContentTypeService;

    @GetMapping
    public ApiResponse<java.util.List<WritingContentTypeResponse>> getContentTypes(
            @PathVariable String langId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<WritingContentTypeResponse> typePage = writingContentTypeService.getContentTypes(langId, search, page, size);

        PageMeta meta = PageMeta.builder()
                .page(page)
                .limit(size)
                .totalElements(typePage.getTotalElements())
                .totalPages(typePage.getTotalPages())
                .build();

        return ApiResponse.<java.util.List<WritingContentTypeResponse>>builder()
                .data(typePage.getContent())
                .meta(meta)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<WritingContentTypeResponse> getContentType(
            @PathVariable String langId, @PathVariable String id) {
        WritingContentTypeResponse result = writingContentTypeService.getContentType(id);
        return ApiResponse.<WritingContentTypeResponse>builder().data(result).build();
    }

    @PostMapping
    public ApiResponse<WritingContentTypeResponse> createContentType(
            @PathVariable String langId, @RequestBody @Valid WritingContentTypeRequest request) {
        WritingContentTypeResponse result = writingContentTypeService.createContentType(langId, request);
        return ApiResponse.<WritingContentTypeResponse>builder().data(result).build();
    }

    @PostMapping("/bulk")
    public ApiResponse<Void> bulkImportContentTypes(
            @PathVariable String langId, @RequestBody java.util.List<WritingContentTypeRequest> requests) {
        writingContentTypeService.bulkImportContentTypes(langId, requests);
        return ApiResponse.<Void>builder().message("Bulk import successful").build();
    }

    @PutMapping("/{id}")
    public ApiResponse<WritingContentTypeResponse> updateContentType(
            @PathVariable String langId,
            @PathVariable String id,
            @RequestBody @Valid WritingContentTypeRequest request) {
        WritingContentTypeResponse result = writingContentTypeService.updateContentType(id, request);
        return ApiResponse.<WritingContentTypeResponse>builder().data(result).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteContentType(@PathVariable String langId, @PathVariable String id) {
        writingContentTypeService.deleteContentType(id);
        return ApiResponse.<Void>builder().message("Deleted successfully").build();
    }
}
