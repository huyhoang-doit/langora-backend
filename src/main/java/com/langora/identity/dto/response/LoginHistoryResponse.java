package com.langora.identity.dto.response;

import java.time.OffsetDateTime;

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
public class LoginHistoryResponse {
    String id;
    String sessionId;
    String ipAddress;
    String userAgent;
    Boolean success;
    String failureReason;
    OffsetDateTime loggedAt;
}
