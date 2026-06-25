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
import com.langora.user.dto.request.UserDeviceRegisterRequest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Client.UserDevices.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDeviceController {

    @GetMapping
    public ApiResponse<List<Object>> getMyDevices() {
        // Placeholder for get devices logic
        return ApiResponse.<List<Object>>builder()
                .message("Fetched devices successfully")
                .build();
    }

    @PostMapping
    public ApiResponse<Object> registerDevice(@RequestBody @Valid UserDeviceRegisterRequest request) {
        // Placeholder for register device logic
        return ApiResponse.builder().message("Registered device successfully").build();
    }
}
