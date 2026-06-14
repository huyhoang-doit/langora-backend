package com.langora.learning.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "lesson_prerequisites")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonPrerequisite {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String lessonId;

    String prerequisiteLessonId;

    java.time.OffsetDateTime createdAt;
}
