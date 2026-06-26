package com.langora.user.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;
import com.langora.shared.service.FileStorageService;
import com.langora.user.domain.entity.UserProfile;
import com.langora.user.dto.request.UserProfileUpdateRequest;
import com.langora.user.dto.response.UserProfileResponse;
import com.langora.user.infrastructure.mapper.UserProfileMapper;
import com.langora.user.repository.UserProfileRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {

    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;
    FileStorageService fileStorageService;

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(String userId) {
        UserProfile userProfile = userProfileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new AppException(ErrorCode.USER_NOT_EXISTED)); // Or a different code if profile doesn't exist
        return userProfileMapper.toResponse(userProfile);
    }

    @Transactional
    public UserProfileResponse updateProfile(String userId, UserProfileUpdateRequest request) {
        UserProfile userProfile = userProfileRepository
                .findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (request.getFullName() != null) userProfile.setFullName(request.getFullName());
        if (request.getDisplayName() != null) userProfile.setDisplayName(request.getDisplayName());
        if (request.getDateOfBirth() != null) userProfile.setDateOfBirth(request.getDateOfBirth());
        if (request.getGender() != null) userProfile.setGender(request.getGender());
        if (request.getCountryCode() != null) userProfile.setCountryCode(request.getCountryCode());
        if (request.getTimezone() != null) userProfile.setTimezone(request.getTimezone());
        if (request.getBio() != null) userProfile.setBio(request.getBio());

        userProfile.setUpdatedAt(OffsetDateTime.now());

        userProfile = userProfileRepository.save(userProfile);

        return userProfileMapper.toResponse(userProfile);
    }

    @Transactional
    public UserProfileResponse updateAvatar(String userId, MultipartFile file) {
        UserProfile userProfile = userProfileRepository
                .findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String avatarUrl = fileStorageService.uploadImage(file, "avatars");
        userProfile.setAvatarUrl(avatarUrl);
        userProfile.setUpdatedAt(OffsetDateTime.now());

        userProfile = userProfileRepository.save(userProfile);

        return userProfileMapper.toResponse(userProfile);
    }
}
