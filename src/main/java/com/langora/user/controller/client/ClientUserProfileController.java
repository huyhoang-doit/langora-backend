package com.langora.user.controller.client;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.shared.utils.SecurityUtils;
import com.langora.user.dto.request.UserProfileUpdateRequest;
import com.langora.user.dto.response.UserProfileResponse;
import com.langora.user.service.UserProfileService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.UserProfiles.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientUserProfileController {

    UserProfileService userProfileService;

    @GetMapping(ApiEndpoint.Client.UserProfiles.ME)
    public ApiResponse<UserProfileResponse> getMyProfile() {
        String userId = SecurityUtils.getCurrentUserId();
        UserProfileResponse result = userProfileService.getProfile(userId);
        return ApiResponse.<UserProfileResponse>builder()
                .data(result)
                .message("Fetched profile successfully")
                .build();
    }

    @PutMapping(ApiEndpoint.Client.UserProfiles.ME)
    public ApiResponse<UserProfileResponse> updateMyProfile(@RequestBody @Valid UserProfileUpdateRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        UserProfileResponse result = userProfileService.updateProfile(userId, request);
        return ApiResponse.<UserProfileResponse>builder()
                .data(result)
                .message("Updated profile successfully")
                .build();
    }

    @PutMapping(ApiEndpoint.Client.UserProfiles.AVATAR)
    public ApiResponse<UserProfileResponse> updateMyAvatar(@RequestParam("file") MultipartFile file) {
        String userId = SecurityUtils.getCurrentUserId();
        UserProfileResponse result = userProfileService.updateAvatar(userId, file);
        return ApiResponse.<UserProfileResponse>builder()
                .data(result)
                .message("Updated avatar successfully")
                .build();
    }
}
