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
import com.langora.user.dto.request.UserLearningGoalUpdateRequest;
import com.langora.user.dto.response.UserLearningGoalResponse;
import com.langora.user.service.UserLearningGoalService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.UserLearningGoals.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserLearningGoalController {

    UserLearningGoalService userLearningGoalService;

    @GetMapping(ApiEndpoint.Client.UserLearningGoals.ME)
    public ApiResponse<UserLearningGoalResponse> getMyLearningGoal() {
        String userId = SecurityUtils.getCurrentUserId();
        UserLearningGoalResponse response = userLearningGoalService.getLearningGoal(userId);
        return ApiResponse.<UserLearningGoalResponse>builder()
                .data(response)
                .message("Fetched learning goal successfully")
                .build();
    }

    @PutMapping(ApiEndpoint.Client.UserLearningGoals.ME)
    public ApiResponse<UserLearningGoalResponse> updateMyLearningGoal(
            @RequestBody @Valid UserLearningGoalUpdateRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        UserLearningGoalResponse response = userLearningGoalService.updateLearningGoal(userId, request);
        return ApiResponse.<UserLearningGoalResponse>builder()
                .data(response)
                .message("Updated learning goal successfully")
                .build();
    }
}
