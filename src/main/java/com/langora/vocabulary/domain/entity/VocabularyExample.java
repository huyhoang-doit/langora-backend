package com.langora.vocabulary.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "vocabulary_examples")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VocabularyExample {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String vocabularyId;

    String exampleSentence;

    String translation;

    String explanation;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
