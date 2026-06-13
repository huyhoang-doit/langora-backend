package com.langora.learning.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.learning.domain.enums.LanguageStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "languages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String code;

    String name;

    String nativeName;

    String flagIconUrl;

    @Enumerated(EnumType.STRING)
    LanguageStatus status;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
