package com.langora.learning.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "lesson_resources")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonResource {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String lessonId;

    String title;

    String resourceType;

    String fileUrl;

    java.time.OffsetDateTime createdAt;
}
