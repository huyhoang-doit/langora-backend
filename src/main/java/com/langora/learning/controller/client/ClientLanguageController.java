package com.langora.learning.controller.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.langora.learning.dto.response.LanguageResponse;
import com.langora.learning.service.LanguageService;
import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.Languages.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientLanguageController {

    LanguageService languageService;

    @GetMapping
    public ApiResponse<List<LanguageResponse>> getLanguages(
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "100") int size) {

        // For client, we usually want all active languages,
        // we can just return the content of the first large page.
        Page<LanguageResponse> languagePage = languageService.getLanguages(page, size);

        return ApiResponse.<List<LanguageResponse>>builder()
                .data(languagePage.getContent())
                .message("Fetched languages successfully")
                .build();
    }
}
