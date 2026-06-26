package com.langora.user.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.langora.user.domain.entity.UserLearningProfile;
import com.langora.user.dto.response.UserLearningProfileResponse;

@Mapper(componentModel = "spring")
public interface UserLearningProfileMapper {
    UserLearningProfileResponse toResponse(UserLearningProfile userLearningProfile);
}
