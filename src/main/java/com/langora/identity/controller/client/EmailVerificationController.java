package com.langora.identity.controller.client;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.identity.dto.request.VerifyEmailRequest;
import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.EmailVerifications.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailVerificationController {

    @PostMapping
    public ApiResponse<Void> verifyEmail(@RequestBody @Valid VerifyEmailRequest request) {
        // Placeholder for verify email logic
        return ApiResponse.<Void>builder()
                .message("Email verified successfully")
                .build();
    }
}
