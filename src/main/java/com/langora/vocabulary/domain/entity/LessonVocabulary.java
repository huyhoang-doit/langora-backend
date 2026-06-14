package com.langora.vocabulary.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "lesson_vocabularies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonVocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String lessonId;

    String vocabularyId;

    Integer displayOrder;

    Boolean isRequired;

    java.time.OffsetDateTime createdAt;
}
