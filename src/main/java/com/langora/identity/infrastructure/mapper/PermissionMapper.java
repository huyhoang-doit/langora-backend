package com.langora.identity.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.langora.identity.domain.entity.Permission;
import com.langora.identity.dto.request.PermissionRequest;
import com.langora.identity.dto.response.PermissionResponse;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
