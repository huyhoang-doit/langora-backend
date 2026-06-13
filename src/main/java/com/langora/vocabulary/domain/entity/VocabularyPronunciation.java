package com.langora.vocabulary.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.vocabulary.domain.enums.PronunciationType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "vocabulary_pronunciations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VocabularyPronunciation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String vocabularyId;

    @Enumerated(EnumType.STRING)
    PronunciationType pronunciationType;

    String audioUrl;

    String speakerName;

    java.time.OffsetDateTime createdAt;
}
