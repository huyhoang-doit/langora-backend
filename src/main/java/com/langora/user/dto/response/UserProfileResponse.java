package com.langora.user.dto.response;

import java.time.LocalDate;

import com.langora.user.domain.enums.GenderType;

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
public class UserProfileResponse {
    String id;
    String userId;
    String fullName;
    String displayName;
    String avatarUrl;
    LocalDate dateOfBirth;
    GenderType gender;
    String countryCode;
    String timezone;
    String bio;
}
