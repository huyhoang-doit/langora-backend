package com.langora.identity.controller.client;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.identity.dto.response.LoginHistoryResponse;
import com.langora.identity.service.LoginHistoryService;
import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.shared.utils.SecurityUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.LoginHistories.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginHistoryController {

    LoginHistoryService loginHistoryService;

    @GetMapping(ApiEndpoint.Client.LoginHistories.ME)
    public ApiResponse<List<LoginHistoryResponse>> getMyLoginHistory() {
        String userId = SecurityUtils.getCurrentUserId();
        List<LoginHistoryResponse> result = loginHistoryService.getMyLoginHistory(userId);
        return ApiResponse.<List<LoginHistoryResponse>>builder()
                .data(result)
                .message("Fetched login history successfully")
                .build();
    }
}
