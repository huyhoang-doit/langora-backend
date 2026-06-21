package com.langora.learning.controller.admin;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.learning.dto.request.LevelRequest;
import com.langora.learning.dto.response.LevelResponse;
import com.langora.learning.service.LevelService;
import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Admin.Levels.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminLevelController {

    LevelService levelService;

    @PutMapping(ApiEndpoint.Admin.Levels.ID)
    public ApiResponse<LevelResponse> updateLevel(@PathVariable String id, @RequestBody @Valid LevelRequest request) {
        LevelResponse result = levelService.updateLevel(id, request);
        return ApiResponse.<LevelResponse>builder().data(result).build();
    }
}
