package com.langora.identity.controller.client;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.LoginHistories.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginHistoryController {

    @GetMapping(ApiEndpoint.Client.LoginHistories.ME)
    public ApiResponse<List<Object>> getMyLoginHistory() {
        // Placeholder for get login history logic
        return ApiResponse.<List<Object>>builder()
                .message("Fetched login history successfully")
                .build();
    }
}
