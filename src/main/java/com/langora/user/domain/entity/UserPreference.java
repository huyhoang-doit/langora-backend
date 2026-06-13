package com.langora.user.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String theme;

    String languageUi;

    String timezone;

    Boolean emailNotificationEnabled;

    Boolean pushNotificationEnabled;

    Boolean reminderEnabled;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
