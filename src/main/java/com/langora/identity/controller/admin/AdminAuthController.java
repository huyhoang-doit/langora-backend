package com.langora.identity.controller.admin;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.identity.dto.request.AdminLoginRequest;
import com.langora.identity.dto.response.AdminAuthResponse;
import com.langora.identity.dto.response.AdminProfileResponse;
import com.langora.identity.service.AuthService;
import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Admin.Auth.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminAuthController {

    AuthService authService;

    @PostMapping(ApiEndpoint.Admin.Auth.LOGIN)
    public ApiResponse<AdminAuthResponse> login(@RequestBody @Valid AdminLoginRequest request) {
        AdminAuthResponse result = authService.login(request);
        return ApiResponse.<AdminAuthResponse>builder().data(result).build();
    }

    @GetMapping(ApiEndpoint.Admin.Auth.ME)
    public ApiResponse<AdminProfileResponse> getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = null;

        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            userId = jwt.getSubject();
        } else if (authentication != null) {
            userId = authentication.getName();
        }

        AdminProfileResponse result = authService.getMe(userId);
        return ApiResponse.<AdminProfileResponse>builder().data(result).build();
    }
}
