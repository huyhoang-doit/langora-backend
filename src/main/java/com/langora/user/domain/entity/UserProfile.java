package com.langora.user.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.user.domain.enums.GenderType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String fullName;

    String displayName;

    String avatarUrl;

    java.time.LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    GenderType gender;

    String countryCode;

    String timezone;

    String bio;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
