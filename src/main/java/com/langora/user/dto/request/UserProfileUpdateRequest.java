package com.langora.user.dto.request;

import java.time.LocalDate;

import com.langora.user.domain.enums.GenderType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileUpdateRequest {

    String fullName;

    String displayName;

    String avatarUrl;

    LocalDate dateOfBirth;

    GenderType gender;

    String countryCode;

    String timezone;

    String bio;
}
