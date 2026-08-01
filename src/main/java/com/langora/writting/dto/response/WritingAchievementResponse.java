package com.langora.writting.dto.response;

import java.time.OffsetDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingAchievementResponse {

    String id;
    String userId;
    String achievementType;
    String title;
    String description;
    OffsetDateTime unlockedAt;
}
