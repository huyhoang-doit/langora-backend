package com.langora.user.service;

import org.springframework.stereotype.Service;

import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;
import com.langora.user.domain.entity.UserLanguageProgress;
import com.langora.user.domain.entity.UserProfile;
import com.langora.user.dto.response.UserProfileResponse;
import com.langora.user.dto.response.UserProgressResponse;
import com.langora.user.infrastructure.mapper.UserActivityMapper;
import com.langora.user.repository.UserLanguageProgressRepository;
import com.langora.user.repository.UserProfileRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserActivityService {

    UserProfileRepository userProfileRepository;
    UserLanguageProgressRepository userLanguageProgressRepository;
    UserActivityMapper userActivityMapper;

    public UserProfileResponse getUserProfile(String userId) {
        UserProfile profile = userProfileRepository
                .findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)); // USER_NOT_FOUND

        return userActivityMapper.toUserProfileResponse(profile);
    }

    public UserProgressResponse getUserProgress(String userId) {
        // Technically there could be multiple languages. For now we assume a single overarching progress
        // or we just return the first one based on api-list.md.
        UserLanguageProgress progress = userLanguageProgressRepository.findByUserId(userId).stream()
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)); // PROGRESS_NOT_FOUND

        return userActivityMapper.toUserProgressResponse(progress);
    }
}
