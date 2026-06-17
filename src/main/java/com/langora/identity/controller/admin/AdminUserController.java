package com.langora.identity.controller.admin;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.langora.identity.domain.enums.UserStatus;
import com.langora.identity.dto.request.UserCreationRequest;
import com.langora.identity.dto.request.UserPasswordUpdateRequest;
import com.langora.identity.dto.request.UserRoleAssignRequest;
import com.langora.identity.dto.request.UserStatusUpdateRequest;
import com.langora.identity.dto.response.LoginHistoryResponse;
import com.langora.identity.dto.response.RoleResponse;
import com.langora.identity.dto.response.UserResponse;
import com.langora.identity.infrastructure.mapper.RoleMapper;
import com.langora.identity.infrastructure.mapper.UserMapper;
import com.langora.identity.service.UserService;
import com.langora.shared.constant.ApiEndpoint;
import com.langora.shared.dto.response.ApiResponse;
import com.langora.shared.dto.response.PageMeta;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApiEndpoint.Admin.Users.BASE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminUserController {

    UserService userService;
    UserMapper userMapper;
    RoleMapper roleMapper;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.createUser(request))
                .message("Created user successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) UserStatus status,
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

        Page<UserResponse> userPage = userService.getUsers(search, status, role, page, limit);
        List<UserResponse> data = userPage.getContent();

        PageMeta pageMeta = PageMeta.builder()
                .page(page)
                .limit(limit)
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .build();

        return ApiResponse.<List<UserResponse>>builder()
                .data(data)
                .meta(pageMeta)
                .message("Fetched users successfully")
                .build();
    }

    @GetMapping(ApiEndpoint.Admin.Users.ID)
    public ApiResponse<UserResponse> getUser(@PathVariable String id) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUser(id))
                .message("Fetched user successfully")
                .build();
    }

    @GetMapping(ApiEndpoint.Admin.Users.ROLES)
    public ApiResponse<List<RoleResponse>> getUserRoles(@PathVariable String id) {
        List<RoleResponse> data = userService.getUserRoles(id).stream()
                .map(roleMapper::toRoleResponse)
                .toList();
        return ApiResponse.<List<RoleResponse>>builder()
                .data(data)
                .message("Fetched user roles successfully")
                .build();
    }

    @PatchMapping(ApiEndpoint.Admin.Users.STATUS)
    public ApiResponse<UserResponse> updateStatus(
            @PathVariable String id, @RequestBody @Valid UserStatusUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.updateStatus(id, request))
                .message("Updated user status successfully")
                .build();
    }

    @PatchMapping(ApiEndpoint.Admin.Users.PASSWORD)
    public ApiResponse<UserResponse> updatePassword(
            @PathVariable String id, @RequestBody @Valid UserPasswordUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.updatePassword(id, request))
                .message("Updated user password successfully")
                .build();
    }

    @PostMapping(ApiEndpoint.Admin.Users.ROLES)
    public ApiResponse<Void> assignRoles(@PathVariable String id, @RequestBody @Valid UserRoleAssignRequest request) {
        userService.assignRoles(id, request);
        return ApiResponse.<Void>builder()
                .message("Assigned roles to user successfully")
                .build();
    }

    @GetMapping(ApiEndpoint.Admin.Users.HISTORY)
    public ApiResponse<List<LoginHistoryResponse>> getLoginHistory(@PathVariable String id) {
        return ApiResponse.<List<LoginHistoryResponse>>builder()
                .data(userService.getLoginHistory(id))
                .message("Fetched user login history successfully")
                .build();
    }
}
