package com.langora.vocabulary.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_custom_vocabularies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCustomVocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String languageId;

    String word;

    String meaning;

    String note;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
