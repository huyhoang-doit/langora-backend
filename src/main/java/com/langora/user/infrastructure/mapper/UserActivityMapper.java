package com.langora.user.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.langora.user.domain.entity.UserLanguageProgress;
import com.langora.user.domain.entity.UserProfile;
import com.langora.user.dto.response.UserProfileResponse;
import com.langora.user.dto.response.UserProgressResponse;

@Mapper
public interface UserActivityMapper {
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);

    UserProgressResponse toUserProgressResponse(UserLanguageProgress userLanguageProgress);
}
