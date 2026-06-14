package com.langora.identity.controller.admin;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langora.identity.dto.request.RoleCreationRequest;
import com.langora.identity.dto.request.RoleUpdateRequest;
import com.langora.identity.dto.response.RoleDetailResponse;
import com.langora.identity.dto.response.RoleResponse;
import com.langora.identity.service.RoleService;
import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Admin.Roles.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminRoleController {

    RoleService roleService;

    @GetMapping
    public ApiResponse<List<RoleResponse>> getRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .data(roleService.getRoles())
                .message("Fetched roles successfully")
                .build();
    }

    @GetMapping(ApiEndpoint.Admin.Roles.ID)
    public ApiResponse<RoleDetailResponse> getRole(@PathVariable String id) {
        return ApiResponse.<RoleDetailResponse>builder()
                .data(roleService.getRole(id))
                .message("Fetched role successfully")
                .build();
    }

    @PostMapping
    public ApiResponse<RoleDetailResponse> createRole(@RequestBody @Valid RoleCreationRequest request) {
        return ApiResponse.<RoleDetailResponse>builder()
                .data(roleService.createRole(request))
                .message("Created role successfully")
                .build();
    }

    @PutMapping(ApiEndpoint.Admin.Roles.ID)
    public ApiResponse<RoleDetailResponse> updateRole(
            @PathVariable String id, @RequestBody @Valid RoleUpdateRequest request) {
        return ApiResponse.<RoleDetailResponse>builder()
                .data(roleService.updateRole(id, request))
                .message("Updated role successfully")
                .build();
    }

    @DeleteMapping(ApiEndpoint.Admin.Roles.ID)
    public ApiResponse<Void> deleteRole(@PathVariable String id) {
        roleService.deleteRole(id);
        return ApiResponse.<Void>builder().message("Deleted role successfully").build();
    }
}
