package com.langora.identity.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.langora.identity.domain.entity.Permission;
import com.langora.identity.domain.repository.PermissionRepository;
import com.langora.identity.dto.request.PermissionRequest;
import com.langora.identity.dto.response.PermissionResponse;
import com.langora.identity.infrastructure.mapper.PermissionMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);

        permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> findAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permission -> permissionMapper.toPermissionResponse(permission))
                .toList();
    }

    public void delete(String name) {
        permissionRepository.deleteById(name);
    }
}
