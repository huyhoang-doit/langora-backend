package com.langora.vocabulary.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "vocabulary_tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VocabularyTag {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String code;

    String name;

    String description;

    java.time.OffsetDateTime createdAt;
}
