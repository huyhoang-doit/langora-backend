package com.langora.user.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.langora.user.domain.entity.UserDevice;
import com.langora.user.dto.response.UserDeviceResponse;

@Mapper(componentModel = "spring")
public interface UserDeviceMapper {
    UserDeviceResponse toResponse(UserDevice userDevice);
}
