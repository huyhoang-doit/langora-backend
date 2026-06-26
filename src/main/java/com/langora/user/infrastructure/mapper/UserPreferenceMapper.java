package com.langora.user.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.langora.user.domain.entity.UserPreference;
import com.langora.user.dto.response.UserPreferenceResponse;

@Mapper(componentModel = "spring")
public interface UserPreferenceMapper {
    UserPreferenceResponse toResponse(UserPreference userPreference);
}
