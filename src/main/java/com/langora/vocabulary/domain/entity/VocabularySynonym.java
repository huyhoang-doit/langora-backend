package com.langora.vocabulary.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "vocabulary_synonyms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VocabularySynonym {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String vocabularyId;

    String synonymVocabularyId;

    java.time.OffsetDateTime createdAt;
}
