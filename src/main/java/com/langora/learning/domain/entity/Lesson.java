package com.langora.learning.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.learning.domain.enums.LessonStatus;
import com.langora.learning.domain.enums.LessonType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String unitId;

    String title;

    String description;

    @Enumerated(EnumType.STRING)
    LessonType lessonType;

    @Enumerated(EnumType.STRING)
    LessonStatus lessonStatus;

    Integer orderIndex;

    Integer estimatedMinutes;

    String thumbnailUrl;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
