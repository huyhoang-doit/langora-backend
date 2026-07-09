package com.langora.identity.controller.client;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.identity.dto.request.ClientLoginRequest;
import com.langora.identity.dto.request.ClientRegisterRequest;
import com.langora.identity.dto.request.GoogleLoginRequest;
import com.langora.identity.dto.request.RefreshTokenRequest;
import com.langora.identity.dto.response.AuthResponse;
import com.langora.identity.service.AuthService;
import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.Auth.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ClientAuthController {

    AuthService authService;

    @PostMapping(ApiEndpoint.Client.Auth.LOGIN)
    public ApiResponse<AuthResponse> login(@RequestBody @Valid ClientLoginRequest request) {
        AuthResponse result = authService.clientLogin(request);
        return ApiResponse.<AuthResponse>builder()
                .data(result)
                .message("Login successfully")
                .build();
    }

    @PostMapping(ApiEndpoint.Client.Auth.REGISTER)
    public ApiResponse<AuthResponse> register(@RequestBody @Valid ClientRegisterRequest request) {
        AuthResponse result = authService.clientRegister(request);
        return ApiResponse.<AuthResponse>builder()
                .data(result)
                .message("Register successfully")
                .build();
    }

    @PostMapping(ApiEndpoint.Client.Auth.REFRESH_TOKEN)
    public ApiResponse<AuthResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        AuthResponse result = authService.refreshToken(request.getRefreshToken());
        return ApiResponse.<AuthResponse>builder()
                .data(result)
                .message("Token refreshed successfully")
                .build();
    }

    @PostMapping(ApiEndpoint.Client.Auth.LOGOUT)
    public ApiResponse<Void> logout(@RequestBody @Valid RefreshTokenRequest request) {
        authService.logout(request.getRefreshToken());
        return ApiResponse.<Void>builder().message("Logout successful").build();
    }

    @PostMapping(ApiEndpoint.Client.Auth.GOOGLE_LOGIN)
    public ApiResponse<AuthResponse> googleLogin(@RequestBody @Valid GoogleLoginRequest request) {
        AuthResponse result = authService.googleLogin(request);
        return ApiResponse.<AuthResponse>builder()
                .data(result)
                .message("Google login successfully")
                .build();
    }
}
