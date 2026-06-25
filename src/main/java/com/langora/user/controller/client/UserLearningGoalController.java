package com.langora.user.controller.client;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.user.dto.request.UserLearningGoalUpdateRequest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.UserLearningGoals.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserLearningGoalController {

    @GetMapping(ApiEndpoint.Client.UserLearningGoals.ME)
    public ApiResponse<Object> getMyLearningGoal() {
        // Placeholder for get learning goal logic
        return ApiResponse.builder()
                .message("Fetched learning goal successfully")
                .build();
    }

    @PutMapping(ApiEndpoint.Client.UserLearningGoals.ME)
    public ApiResponse<Object> updateMyLearningGoal(@RequestBody @Valid UserLearningGoalUpdateRequest request) {
        // Placeholder for update learning goal logic
        return ApiResponse.builder()
                .message("Updated learning goal successfully")
                .build();
    }
}
