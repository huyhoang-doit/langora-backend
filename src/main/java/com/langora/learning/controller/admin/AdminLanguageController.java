package com.langora.learning.controller.admin;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.langora.learning.dto.request.LevelRequest;
import com.langora.learning.dto.response.LanguageResponse;
import com.langora.learning.dto.response.LevelResponse;
import com.langora.learning.service.LanguageService;
import com.langora.learning.service.LevelService;
import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.shared.dto.response.PageMeta;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Admin.Languages.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminLanguageController {

    LanguageService languageService;
    LevelService levelService;

    @GetMapping
    public ApiResponse<java.util.List<LanguageResponse>> getLanguages(
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {

        Page<LanguageResponse> languagePage = languageService.getLanguages(page, size);

        PageMeta meta = PageMeta.builder()
                .page(page)
                .limit(size)
                .totalElements(languagePage.getTotalElements())
                .totalPages(languagePage.getTotalPages())
                .build();

        return ApiResponse.<java.util.List<LanguageResponse>>builder()
                .data(languagePage.getContent())
                .meta(meta)
                .build();
    }

    @PatchMapping(ApiEndpoint.Admin.Languages.STATUS)
    public ApiResponse<LanguageResponse> toggleStatus(@PathVariable String id) {
        LanguageResponse result = languageService.toggleLanguageStatus(id);
        return ApiResponse.<LanguageResponse>builder().data(result).build();
    }

    @GetMapping(ApiEndpoint.Admin.Languages.LEVELS)
    public ApiResponse<java.util.List<LevelResponse>> getLevelsByLanguage(
            @PathVariable String langId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<LevelResponse> levelPage = levelService.getLevelsByLanguage(langId, page, size);

        PageMeta meta = PageMeta.builder()
                .page(page)
                .limit(size)
                .totalElements(levelPage.getTotalElements())
                .totalPages(levelPage.getTotalPages())
                .build();

        return ApiResponse.<java.util.List<LevelResponse>>builder()
                .data(levelPage.getContent())
                .meta(meta)
                .build();
    }

    @PostMapping(ApiEndpoint.Admin.Languages.LEVELS)
    public ApiResponse<LevelResponse> createLevel(
            @PathVariable String langId, @RequestBody @Valid LevelRequest request) {
        LevelResponse result = levelService.createLevel(langId, request);
        return ApiResponse.<LevelResponse>builder().data(result).build();
    }
}
