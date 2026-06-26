package com.langora.user.controller.client;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.shared.utils.SecurityUtils;
import com.langora.user.dto.request.UserDeviceRegisterRequest;
import com.langora.user.dto.response.UserDeviceResponse;
import com.langora.user.service.UserDeviceService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.UserDevices.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDeviceController {

    UserDeviceService userDeviceService;

    @GetMapping
    public ApiResponse<List<UserDeviceResponse>> getMyDevices() {
        String userId = SecurityUtils.getCurrentUserId();
        List<UserDeviceResponse> response = userDeviceService.getDevices(userId);
        return ApiResponse.<List<UserDeviceResponse>>builder()
                .data(response)
                .message("Fetched devices successfully")
                .build();
    }

    @PostMapping
    public ApiResponse<UserDeviceResponse> registerDevice(@RequestBody @Valid UserDeviceRegisterRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        UserDeviceResponse response = userDeviceService.registerDevice(userId, request);
        return ApiResponse.<UserDeviceResponse>builder()
                .data(response)
                .message("Device registered successfully")
                .build();
    }
}
