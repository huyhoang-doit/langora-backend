package com.langora.vocabulary.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "vocabulary_antonyms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VocabularyAntonym {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String vocabularyId;

    String antonymVocabularyId;

    java.time.OffsetDateTime createdAt;
}
