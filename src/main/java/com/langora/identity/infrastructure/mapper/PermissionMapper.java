package com.langora.identity.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.langora.identity.domain.entity.Permission;
import com.langora.identity.dto.response.PermissionResponse;

@Mapper
public interface PermissionMapper {
    PermissionResponse toPermissionResponse(Permission permission);
}
