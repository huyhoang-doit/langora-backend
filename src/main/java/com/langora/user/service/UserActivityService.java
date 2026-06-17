package com.langora.user.service;

import org.springframework.stereotype.Service;

import com.langora.user.domain.entity.UserLanguageProgress;
import com.langora.user.domain.entity.UserProfile;
import com.langora.user.dto.request.UserProfileUpdateRequest;
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
        UserProfile profile = userProfileRepository.findByUserId(userId).orElseGet(() -> {
            UserProfile newProfile = UserProfile.builder()
                    .userId(userId)
                    .createdAt(java.time.OffsetDateTime.now())
                    .updatedAt(java.time.OffsetDateTime.now())
                    .build();
            return userProfileRepository.save(newProfile);
        });

        return userActivityMapper.toUserProfileResponse(profile);
    }

    public UserProgressResponse getUserProgress(String userId) {
        // Technically there could be multiple languages. For now we assume a single overarching progress
        // or we just return the first one based on api-list.md.
        UserLanguageProgress progress = userLanguageProgressRepository.findByUserId(userId).stream()
                .findFirst()
                .orElseGet(() -> {
                    UserLanguageProgress newProgress = UserLanguageProgress.builder()
                            .userId(userId)
                            .totalLearnedWords(0)
                            .totalMasteredWords(0)
                            .totalLessonsCompleted(0)
                            .totalStudyMinutes(0)
                            .currentStreak(0)
                            .longestStreak(0)
                            .createdAt(java.time.OffsetDateTime.now())
                            .updatedAt(java.time.OffsetDateTime.now())
                            .build();
                    return userLanguageProgressRepository.save(newProgress);
                });

        return userActivityMapper.toUserProgressResponse(progress);
    }

    public UserProfileResponse updateUserProfile(String userId, UserProfileUpdateRequest request) {
        UserProfile profile = userProfileRepository.findByUserId(userId).orElseGet(() -> UserProfile.builder()
                .userId(userId)
                .createdAt(java.time.OffsetDateTime.now())
                .build());

        if (request.getFullName() != null) profile.setFullName(request.getFullName());
        if (request.getDisplayName() != null) profile.setDisplayName(request.getDisplayName());
        if (request.getAvatarUrl() != null) profile.setAvatarUrl(request.getAvatarUrl());
        if (request.getDateOfBirth() != null) profile.setDateOfBirth(request.getDateOfBirth());
        if (request.getGender() != null) profile.setGender(request.getGender());
        if (request.getCountryCode() != null) profile.setCountryCode(request.getCountryCode());
        if (request.getTimezone() != null) profile.setTimezone(request.getTimezone());
        if (request.getBio() != null) profile.setBio(request.getBio());

        profile.setUpdatedAt(java.time.OffsetDateTime.now());
        userProfileRepository.save(profile);

        return userActivityMapper.toUserProfileResponse(profile);
    }
}
