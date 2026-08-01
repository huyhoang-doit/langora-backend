package com.langora.writting.controller.client;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.shared.utils.SecurityUtils;
import com.langora.writting.dto.response.WritingAchievementResponse;
import com.langora.writting.service.WritingAchievementService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.WritingAchievements.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientWritingAchievementController {

    WritingAchievementService writingAchievementService;

    @GetMapping(ApiEndpoint.Client.WritingAchievements.ME)
    public ApiResponse<List<WritingAchievementResponse>> getMyAchievements() {
        String userId = SecurityUtils.getCurrentUserId();
        List<WritingAchievementResponse> responses = writingAchievementService.getMyAchievements(userId);

        return ApiResponse.<List<WritingAchievementResponse>>builder()
                .data(responses)
                .message("Fetched achievements successfully")
                .build();
    }
}
