package com.langora.user.controller.client;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.shared.utils.SecurityUtils;
import com.langora.user.dto.request.UserLearningProfileUpdateRequest;
import com.langora.user.dto.response.UserLearningProfileResponse;
import com.langora.user.service.UserLearningProfileService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.UserLearningProfiles.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserLearningProfileController {

    UserLearningProfileService userLearningProfileService;

    @GetMapping(ApiEndpoint.Client.UserLearningProfiles.ME)
    public ApiResponse<UserLearningProfileResponse> getMyLearningProfile() {
        String userId = SecurityUtils.getCurrentUserId();
        UserLearningProfileResponse response = userLearningProfileService.getLearningProfile(userId);
        return ApiResponse.<UserLearningProfileResponse>builder()
                .data(response)
                .message("Fetched learning profile successfully")
                .build();
    }

    @PutMapping(ApiEndpoint.Client.UserLearningProfiles.ME)
    public ApiResponse<UserLearningProfileResponse> updateMyLearningProfile(
            @RequestBody @Valid UserLearningProfileUpdateRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        UserLearningProfileResponse response = userLearningProfileService.updateLearningProfile(userId, request);
        return ApiResponse.<UserLearningProfileResponse>builder()
                .data(response)
                .message("Updated learning profile successfully")
                .build();
    }
}
