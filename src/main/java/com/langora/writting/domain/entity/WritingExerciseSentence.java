package com.langora.writting.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "writing_exercise_sentences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingExerciseSentence {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String exerciseId;

    Integer sentenceOrder;

    String sourceText;

    String targetText;

    String vocabularyHints;

    String grammarHints;

    java.math.BigDecimal difficultyScore;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
