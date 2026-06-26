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
import com.langora.user.dto.request.UserPreferenceUpdateRequest;
import com.langora.user.dto.response.UserPreferenceResponse;
import com.langora.user.service.UserPreferenceService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.UserPreferences.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserPreferenceController {

    UserPreferenceService userPreferenceService;

    @GetMapping(ApiEndpoint.Client.UserPreferences.ME)
    public ApiResponse<UserPreferenceResponse> getMyPreferences() {
        String userId = SecurityUtils.getCurrentUserId();
        UserPreferenceResponse response = userPreferenceService.getPreference(userId);
        return ApiResponse.<UserPreferenceResponse>builder()
                .data(response)
                .message("Fetched preference successfully")
                .build();
    }

    @PutMapping(ApiEndpoint.Client.UserPreferences.ME)
    public ApiResponse<UserPreferenceResponse> updateMyPreferences(
            @RequestBody @Valid UserPreferenceUpdateRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        UserPreferenceResponse response = userPreferenceService.updatePreference(userId, request);
        return ApiResponse.<UserPreferenceResponse>builder()
                .data(response)
                .message("Updated preference successfully")
                .build();
    }
}
