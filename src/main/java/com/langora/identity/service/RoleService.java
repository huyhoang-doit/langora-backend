package com.langora.identity.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.langora.identity.domain.entity.Role;
import com.langora.identity.domain.entity.RolePermission;
import com.langora.identity.dto.request.RoleCreationRequest;
import com.langora.identity.dto.request.RoleUpdateRequest;
import com.langora.identity.dto.response.PermissionResponse;
import com.langora.identity.dto.response.RoleDetailResponse;
import com.langora.identity.dto.response.RoleResponse;
import com.langora.identity.infrastructure.mapper.PermissionMapper;
import com.langora.identity.infrastructure.mapper.RoleMapper;
import com.langora.identity.repository.PermissionRepository;
import com.langora.identity.repository.RolePermissionRepository;
import com.langora.identity.repository.RoleRepository;
import com.langora.identity.repository.UserRoleRepository;
import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RolePermissionRepository rolePermissionRepository;
    UserRoleRepository userRoleRepository;
    RoleMapper roleMapper;
    PermissionMapper permissionMapper;

    public List<RoleResponse> getRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public RoleDetailResponse getRole(String id) {
        Role role = roleRepository
                .findById(id)
                .orElseThrow(() -> new AppException(
                        ErrorCode.INVALID_KEY)); // You might want to create a ROLE_NOT_FOUND error code

        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(id);
        List<String> permissionIds =
                rolePermissions.stream().map(RolePermission::getPermissionId).toList();

        List<PermissionResponse> permissions = permissionRepository.findAllById(permissionIds).stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();

        RoleDetailResponse response = roleMapper.toRoleDetailResponse(role);
        response.setPermissions(permissions);
        return response;
    }

    @Transactional
    public RoleDetailResponse createRole(RoleCreationRequest request) {
        if (roleRepository.findByCode(request.getCode()).isPresent()) {
            throw new AppException(ErrorCode.INVALID_KEY); // Should be ROLE_ALREADY_EXISTS
        }

        Role role = Role.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .isSystem(false)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
        role = roleRepository.save(role);

        if (request.getPermissionIds() != null && !request.getPermissionIds().isEmpty()) {
            final String roleId = role.getId();
            List<RolePermission> rolePermissions = request.getPermissionIds().stream()
                    .map(permissionId -> RolePermission.builder()
                            .roleId(roleId)
                            .permissionId(permissionId)
                            .build())
                    .toList();
            rolePermissionRepository.saveAll(rolePermissions);
        }

        return getRole(role.getId());
    }

    @Transactional
    public RoleDetailResponse updateRole(String id, RoleUpdateRequest request) {
        Role role = roleRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)); // ROLE_NOT_FOUND

        if (Boolean.TRUE.equals(role.getIsSystem())) {
            throw new AppException(ErrorCode.INVALID_KEY); // Cannot modify system role
        }

        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setUpdatedAt(OffsetDateTime.now());
        role = roleRepository.save(role);

        if (request.getPermissionIds() != null) {
            rolePermissionRepository.deleteByRoleId(id);
            List<RolePermission> rolePermissions = request.getPermissionIds().stream()
                    .map(permissionId -> RolePermission.builder()
                            .roleId(id)
                            .permissionId(permissionId)
                            .build())
                    .toList();
            rolePermissionRepository.saveAll(rolePermissions);
        }

        return getRole(id);
    }

    @Transactional
    public void deleteRole(String id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        if (Boolean.TRUE.equals(role.getIsSystem())) {
            throw new AppException(ErrorCode.INVALID_KEY); // Cannot delete system role
        }

        if (!userRoleRepository.findByRoleId(id).isEmpty()) {
            throw new AppException(ErrorCode.INVALID_KEY); // ROLE_IN_USE
        }

        rolePermissionRepository.deleteByRoleId(id);
        roleRepository.delete(role);
    }
}
