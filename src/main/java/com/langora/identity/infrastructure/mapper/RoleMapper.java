package com.langora.identity.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.langora.identity.domain.entity.Role;
import com.langora.identity.dto.response.RoleDetailResponse;
import com.langora.identity.dto.response.RoleResponse;

@Mapper
public interface RoleMapper {
    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissions", ignore = true)
    RoleDetailResponse toRoleDetailResponse(Role role);
}
