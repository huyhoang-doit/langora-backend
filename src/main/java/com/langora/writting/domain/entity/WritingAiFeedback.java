package com.langora.writting.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "writing_ai_feedbacks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingAiFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String answerId;

    String aiProvider;

    String aiModel;

    String overallFeedback;

    String grammarFeedback;

    String vocabularyFeedback;

    String fluencyFeedback;

    String suggestedAnswer;

    String correctionJson;

    Integer processingTimeMs;

    java.time.OffsetDateTime createdAt;
}
