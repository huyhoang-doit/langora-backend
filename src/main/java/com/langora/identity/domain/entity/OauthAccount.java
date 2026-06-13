package com.langora.identity.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.identity.domain.enums.ProviderType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "oauth_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OauthAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    @Enumerated(EnumType.STRING)
    ProviderType provider;

    String providerUserId;

    String email;

    String avatarUrl;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
