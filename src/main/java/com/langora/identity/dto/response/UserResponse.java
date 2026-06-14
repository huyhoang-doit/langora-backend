package com.langora.identity.dto.response;

import java.time.OffsetDateTime;

import com.langora.identity.domain.enums.UserStatus;

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
public class UserResponse {
    String id;
    String email;
    UserStatus status;
    Boolean emailVerified;
    OffsetDateTime lastLoginAt;
    OffsetDateTime createdAt;
}
