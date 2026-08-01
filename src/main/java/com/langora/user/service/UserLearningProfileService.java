package com.langora.user.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.langora.user.domain.entity.UserLearningProfile;
import com.langora.user.dto.request.UserLearningProfileUpdateRequest;
import com.langora.user.dto.response.UserLearningProfileResponse;
import com.langora.user.infrastructure.mapper.UserLearningProfileMapper;
import com.langora.user.repository.UserLearningProfileRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserLearningProfileService {

    UserLearningProfileRepository userLearningProfileRepository;
    UserLearningProfileMapper userLearningProfileMapper;

    @Transactional(readOnly = true)
    public UserLearningProfileResponse getLearningProfile(String userId) {
        UserLearningProfile profile = userLearningProfileRepository
                .findByUserId(userId)
                .orElseGet(() -> createDefaultLearningProfile(userId));
        return userLearningProfileMapper.toResponse(profile);
    }

    @Transactional
    public UserLearningProfileResponse updateLearningProfile(String userId, UserLearningProfileUpdateRequest request) {
        UserLearningProfile profile = userLearningProfileRepository
                .findByUserId(userId)
                .orElseGet(() -> createDefaultLearningProfile(userId));

        if (request.getTargetLanguageId() != null) profile.setTargetLanguageId(request.getTargetLanguageId());
        if (request.getCurrentLevelId() != null) profile.setCurrentLevelId(request.getCurrentLevelId());
        if (request.getLearningGoal() != null) profile.setLearningGoal(request.getLearningGoal());
        if (request.getTargetExam() != null) profile.setTargetExam(request.getTargetExam());
        if (request.getDailyGoalMinutes() != null) profile.setDailyGoalMinutes(request.getDailyGoalMinutes());
        if (request.getDailyGoalWords() != null) profile.setDailyGoalWords(request.getDailyGoalWords());

        profile.setUpdatedAt(OffsetDateTime.now());
        userLearningProfileRepository.save(profile);

        return userLearningProfileMapper.toResponse(profile);
    }

    private UserLearningProfile createDefaultLearningProfile(String userId) {
        return userLearningProfileRepository.save(UserLearningProfile.builder()
                .userId(userId)
                .isActive(true)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build());
    }
}
