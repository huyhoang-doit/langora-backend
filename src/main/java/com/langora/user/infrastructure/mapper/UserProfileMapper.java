package com.langora.user.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.langora.user.domain.entity.UserProfile;
import com.langora.user.dto.response.UserProfileResponse;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfileResponse toResponse(UserProfile userProfile);
}
