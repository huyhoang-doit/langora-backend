package com.langora.user.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.user.domain.enums.DeviceType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_devices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    @Enumerated(EnumType.STRING)
    DeviceType deviceType;

    String deviceToken;

    String deviceName;

    String operatingSystem;

    String appVersion;

    Boolean isActive;

    java.time.OffsetDateTime lastActiveAt;

    java.time.OffsetDateTime createdAt;
}
