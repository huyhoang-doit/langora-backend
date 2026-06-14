package com.langora.identity.infrastructure.mapper;

import com.langora.identity.domain.entity.Role;
import com.langora.identity.dto.response.RoleDetailResponse;
import com.langora.identity.dto.response.RoleResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Homebrew)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleResponse toRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.id( role.getId() );
        roleResponse.code( role.getCode() );
        roleResponse.name( role.getName() );
        roleResponse.description( role.getDescription() );
        roleResponse.isSystem( role.getIsSystem() );

        return roleResponse.build();
    }

    @Override
    public RoleDetailResponse toRoleDetailResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDetailResponse.RoleDetailResponseBuilder roleDetailResponse = RoleDetailResponse.builder();

        roleDetailResponse.id( role.getId() );
        roleDetailResponse.code( role.getCode() );
        roleDetailResponse.name( role.getName() );
        roleDetailResponse.description( role.getDescription() );
        roleDetailResponse.isSystem( role.getIsSystem() );

        return roleDetailResponse.build();
    }
}
