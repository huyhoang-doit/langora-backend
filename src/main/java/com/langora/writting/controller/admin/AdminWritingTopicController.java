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
import com.langora.writting.dto.request.WritingTopicRequest;
import com.langora.writting.dto.response.WritingTopicResponse;
import com.langora.writting.service.WritingTopicService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Admin.Languages.BASE + ApiEndpoint.Admin.Languages.WRITING_TOPICS)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminWritingTopicController {

    WritingTopicService writingTopicService;

    @GetMapping
    public ApiResponse<java.util.List<WritingTopicResponse>> getTopics(
            @PathVariable String langId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<WritingTopicResponse> topicPage = writingTopicService.getTopics(langId, page, size);

        PageMeta meta = PageMeta.builder()
                .page(page)
                .limit(size)
                .totalElements(topicPage.getTotalElements())
                .totalPages(topicPage.getTotalPages())
                .build();

        return ApiResponse.<java.util.List<WritingTopicResponse>>builder()
                .data(topicPage.getContent())
                .meta(meta)
                .build();
    }

    @PostMapping
    public ApiResponse<WritingTopicResponse> createTopic(
            @PathVariable String langId, @RequestBody @Valid WritingTopicRequest request) {
        WritingTopicResponse result = writingTopicService.createTopic(langId, request);
        return ApiResponse.<WritingTopicResponse>builder().data(result).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<WritingTopicResponse> getTopic(@PathVariable String langId, @PathVariable String id) {
        WritingTopicResponse result = writingTopicService.getTopic(id);
        return ApiResponse.<WritingTopicResponse>builder().data(result).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<WritingTopicResponse> updateTopic(
            @PathVariable String langId, @PathVariable String id, @RequestBody @Valid WritingTopicRequest request) {
        WritingTopicResponse result = writingTopicService.updateTopic(id, request);
        return ApiResponse.<WritingTopicResponse>builder().data(result).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTopic(@PathVariable String langId, @PathVariable String id) {
        writingTopicService.deleteTopic(id);
        return ApiResponse.<Void>builder().message("Deleted successfully").build();
    }
}
