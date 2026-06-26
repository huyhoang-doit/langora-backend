package com.langora.learning.controller.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.langora.learning.dto.response.LevelResponse;
import com.langora.learning.service.LevelService;
import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.Languages.BASE + ApiEndpoint.Client.Languages.LEVELS)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientLevelController {

    LevelService levelService;

    @GetMapping
    public ApiResponse<List<LevelResponse>> getLevelsByLanguage(
            @PathVariable String langId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "100") int size) {

        Page<LevelResponse> levelPage = levelService.getLevelsByLanguage(langId, null, page, size);

        return ApiResponse.<List<LevelResponse>>builder()
                .data(levelPage.getContent())
                .message("Fetched levels successfully")
                .build();
    }
}
