package com.langora.user.controller.client;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.user.dto.request.UserLearningProfileUpdateRequest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.UserLearningProfiles.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserLearningProfileController {

    @GetMapping(ApiEndpoint.Client.UserLearningProfiles.ME)
    public ApiResponse<Object> getMyLearningProfile() {
        // Placeholder for get learning profile logic
        return ApiResponse.builder()
                .message("Fetched learning profile successfully")
                .build();
    }

    @PutMapping(ApiEndpoint.Client.UserLearningProfiles.ME)
    public ApiResponse<Object> updateMyLearningProfile(@RequestBody @Valid UserLearningProfileUpdateRequest request) {
        // Placeholder for update learning profile logic
        return ApiResponse.builder()
                .message("Updated learning profile successfully")
                .build();
    }
}
