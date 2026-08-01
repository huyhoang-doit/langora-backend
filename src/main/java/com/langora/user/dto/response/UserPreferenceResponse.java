package com.langora.user.dto.response;

import java.time.OffsetDateTime;

import com.langora.learning.dto.response.LevelResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPreferenceResponse {
    String id;
    String theme;
    String languageUi;
    String timezone;
    LevelResponse level;
    Boolean emailNotificationEnabled;
    Boolean pushNotificationEnabled;
    Boolean reminderEnabled;
    OffsetDateTime updatedAt;
}
