package com.langora.writting.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "writing_sentence_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingSentenceAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String sessionId;

    String sentenceId;

    String userAnswer;

    java.math.BigDecimal aiScore;

    java.math.BigDecimal grammarScore;

    java.math.BigDecimal vocabularyScore;

    java.math.BigDecimal fluencyScore;

    java.math.BigDecimal accuracyScore;

    java.time.OffsetDateTime submittedAt;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
