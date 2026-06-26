package com.langora.user.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public UserPreferenceResponse getPreference(String userId) {
        UserPreference preference =
                userPreferenceRepository.findByUserId(userId).orElseGet(() -> createDefaultPreference(userId));
        return userPreferenceMapper.toResponse(preference);
    }

    @Transactional
    public UserPreferenceResponse updatePreference(String userId, UserPreferenceUpdateRequest request) {
        UserPreference preference =
                userPreferenceRepository.findByUserId(userId).orElseGet(() -> createDefaultPreference(userId));

        if (request.getTheme() != null) preference.setTheme(request.getTheme());
        if (request.getLanguageUi() != null) preference.setLanguageUi(request.getLanguageUi());
        if (request.getTimezone() != null) preference.setTimezone(request.getTimezone());
        if (request.getEmailNotificationEnabled() != null)
            preference.setEmailNotificationEnabled(request.getEmailNotificationEnabled());
        if (request.getPushNotificationEnabled() != null)
            preference.setPushNotificationEnabled(request.getPushNotificationEnabled());
        if (request.getReminderEnabled() != null) preference.setReminderEnabled(request.getReminderEnabled());

        preference.setUpdatedAt(OffsetDateTime.now());
        userPreferenceRepository.save(preference);

        return userPreferenceMapper.toResponse(preference);
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
