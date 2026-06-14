package com.langora.user.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.user.dto.response.UserProfileResponse;
import com.langora.user.dto.response.UserProgressResponse;
import com.langora.user.service.UserActivityService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Admin.UserProfiles.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminUserActivityController {

    UserActivityService userActivityService;

    @GetMapping(ApiEndpoint.Admin.UserProfiles.ID)
    public ApiResponse<UserProfileResponse> getUserProfile(@PathVariable String userId) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userActivityService.getUserProfile(userId))
                .message("Fetched user profile successfully")
                .build();
    }

    @GetMapping(ApiEndpoint.Admin.UserProfiles.PROGRESS)
    public ApiResponse<UserProgressResponse> getUserProgress(@PathVariable String userId) {
        return ApiResponse.<UserProgressResponse>builder()
                .data(userActivityService.getUserProgress(userId))
                .message("Fetched user progress successfully")
                .build();
    }
}
