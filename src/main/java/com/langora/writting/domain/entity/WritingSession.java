package com.langora.writting.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.writting.domain.enums.WritingSessionStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "writing_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String userId;

    String exerciseId;

    Integer currentSentenceOrder;

    @Enumerated(EnumType.STRING)
    WritingSessionStatus status;

    java.math.BigDecimal totalScore;

    java.math.BigDecimal grammarScore;

    java.math.BigDecimal vocabularyScore;

    java.math.BigDecimal fluencyScore;

    java.math.BigDecimal accuracyScore;

    Integer creditsEarned;

    Integer xpEarned;

    java.time.OffsetDateTime startedAt;

    java.time.OffsetDateTime submittedAt;

    java.time.OffsetDateTime completedAt;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
