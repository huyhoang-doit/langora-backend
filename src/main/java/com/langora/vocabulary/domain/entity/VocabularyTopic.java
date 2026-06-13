package com.langora.vocabulary.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "vocabulary_topics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VocabularyTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String languageId;

    String code;

    String name;

    String description;

    String iconUrl;

    Integer displayOrder;

    Boolean isActive;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
