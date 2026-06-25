package com.langora.user.controller.client;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.user.dto.request.UserPreferenceUpdateRequest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.UserPreferences.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserPreferenceController {

    @GetMapping(ApiEndpoint.Client.UserPreferences.ME)
    public ApiResponse<Object> getMyPreferences() {
        // Placeholder for get preferences logic
        return ApiResponse.builder().message("Fetched preferences successfully").build();
    }

    @PutMapping(ApiEndpoint.Client.UserPreferences.ME)
    public ApiResponse<Object> updateMyPreferences(@RequestBody @Valid UserPreferenceUpdateRequest request) {
        // Placeholder for update preferences logic
        return ApiResponse.builder().message("Updated preferences successfully").build();
    }
}
