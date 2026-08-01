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
import com.langora.writting.dto.response.WritingTopicResponse;
import com.langora.writting.service.WritingTopicService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.Languages.BASE + ApiEndpoint.Client.Languages.WRITING_TOPICS)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientWritingTopicController {

    WritingTopicService writingTopicService;

    @GetMapping
    public ApiResponse<List<WritingTopicResponse>> getTopics(
            @PathVariable String langId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "100") int size) {

        // The levelId could be passed as query string and handled, but currently WritingTopic doesn't filter by levelId
        // in DB,
        // it filters by languageId and search string. If level filtering is needed, it should be added to Service.
        Page<WritingTopicResponse> topicPage = writingTopicService.getTopics(langId, search, page, size);

        return ApiResponse.<List<WritingTopicResponse>>builder()
                .data(topicPage.getContent())
                .message("Fetched topics successfully")
                .build();
    }
}
