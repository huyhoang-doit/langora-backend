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
import com.langora.writting.dto.response.WritingContentTypeResponse;
import com.langora.writting.service.WritingContentTypeService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.Languages.BASE + ApiEndpoint.Client.Languages.WRITING_CONTENT_TYPES)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientWritingContentTypeController {

    WritingContentTypeService writingContentTypeService;

    @GetMapping
    public ApiResponse<List<WritingContentTypeResponse>> getContentTypes(
            @PathVariable String langId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "100") int size) {

        Page<WritingContentTypeResponse> typePage =
                writingContentTypeService.getContentTypes(langId, search, page, size);

        return ApiResponse.<List<WritingContentTypeResponse>>builder()
                .data(typePage.getContent())
                .message("Fetched content types successfully")
                .build();
    }
}
