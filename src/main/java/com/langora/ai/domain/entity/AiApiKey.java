package com.langora.ai.domain.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "ai_api_keys")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AiApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String provider;

    String rawKey;

    String usage;

    String rank;

    Boolean active;

    OffsetDateTime createdAt;

    OffsetDateTime updatedAt;
}
