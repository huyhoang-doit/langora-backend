package com.langora.identity.application.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.langora.identity.domain.entity.Role;
import com.langora.identity.domain.repository.PermissionRepository;
import com.langora.identity.domain.repository.RoleRepository;
import com.langora.identity.dto.request.RoleRequest;
import com.langora.identity.dto.response.RoleResponse;
import com.langora.identity.infrastructure.mapper.RoleMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {

    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> roleMapper.toRoleResponse(role)).toList();
    }

    public void delete(String name) {
        roleRepository.deleteById(name);
    }
}
