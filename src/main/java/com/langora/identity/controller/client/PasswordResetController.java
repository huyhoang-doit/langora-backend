package com.langora.identity.controller.client;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.identity.dto.request.ForgotPasswordRequest;
import com.langora.identity.dto.request.ResetPasswordRequest;
import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.PasswordResets.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PasswordResetController {

    @PostMapping(ApiEndpoint.Client.PasswordResets.REQUEST)
    public ApiResponse<Void> requestPasswordReset(@RequestBody @Valid ForgotPasswordRequest request) {
        // Placeholder for forgot password logic
        return ApiResponse.<Void>builder()
                .message("Password reset link sent to email")
                .build();
    }

    @PostMapping(ApiEndpoint.Client.PasswordResets.RESET)
    public ApiResponse<Void> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        // Placeholder for reset password logic
        return ApiResponse.<Void>builder()
                .message("Password reset successfully")
                .build();
    }
}
