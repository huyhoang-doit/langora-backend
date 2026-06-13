package com.langora.vocabulary.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.vocabulary.domain.enums.DifficultyLevel;
import com.langora.vocabulary.domain.enums.VocabularyStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "vocabularies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String languageId;

    String topicId;

    String word;

    String normalizedWord;

    String meaning;

    String ipa;

    String phonetic;

    @Enumerated(EnumType.STRING)
    DifficultyLevel difficulty;

    Integer frequencyRank;

    String imageUrl;

    @Enumerated(EnumType.STRING)
    VocabularyStatus status;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
