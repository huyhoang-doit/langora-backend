package com.langora.user.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.langora.learning.domain.repository.LevelRepository;
import com.langora.learning.infrastructure.mapper.LevelMapper;
import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;
import com.langora.user.domain.entity.UserPreference;
import com.langora.user.dto.request.UserPreferenceUpdateRequest;
import com.langora.user.dto.response.UserPreferenceResponse;
import com.langora.user.infrastructure.mapper.UserPreferenceMapper;
import com.langora.user.repository.UserPreferenceRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserPreferenceService {

    UserPreferenceRepository userPreferenceRepository;
    UserPreferenceMapper userPreferenceMapper;
    LevelRepository levelRepository;
    LevelMapper levelMapper;

    @Transactional(readOnly = true)
    public UserPreferenceResponse getPreference(String userId) {
        UserPreference preference =
                userPreferenceRepository.findByUserId(userId).orElseGet(() -> createDefaultPreference(userId));
        return buildResponse(preference);
    }

    @Transactional
    public UserPreferenceResponse updatePreference(String userId, UserPreferenceUpdateRequest request) {
        UserPreference preference =
                userPreferenceRepository.findByUserId(userId).orElseGet(() -> createDefaultPreference(userId));

        if (request.getTheme() != null) preference.setTheme(request.getTheme());
        if (request.getLanguageUi() != null) preference.setLanguageUi(request.getLanguageUi());
        if (request.getTimezone() != null) preference.setTimezone(request.getTimezone());
        if (request.getLevelId() != null) {
            levelRepository.findById(request.getLevelId())
                    .orElseThrow(() -> new AppException(ErrorCode.LEARNING_LEVEL_NOT_FOUND));
            preference.setLevelId(request.getLevelId());
        }
        if (request.getEmailNotificationEnabled() != null)
            preference.setEmailNotificationEnabled(request.getEmailNotificationEnabled());
        if (request.getPushNotificationEnabled() != null)
            preference.setPushNotificationEnabled(request.getPushNotificationEnabled());
        if (request.getReminderEnabled() != null) preference.setReminderEnabled(request.getReminderEnabled());

        preference.setUpdatedAt(OffsetDateTime.now());
        userPreferenceRepository.save(preference);

        return buildResponse(preference);
    }

    private UserPreferenceResponse buildResponse(UserPreference preference) {
        UserPreferenceResponse response = userPreferenceMapper.toResponse(preference);
        if (preference.getLevelId() != null) {
            levelRepository.findById(preference.getLevelId()).ifPresent(level -> 
                response.setLevel(levelMapper.toResponse(level))
            );
        }
        return response;
    }

    private UserPreference createDefaultPreference(String userId) {
        return userPreferenceRepository.save(UserPreference.builder()
                .userId(userId)
                .theme("LIGHT")
                .languageUi("en")
                .timezone("UTC")
                .emailNotificationEnabled(true)
                .pushNotificationEnabled(true)
                .reminderEnabled(true)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build());
    }
}
