package com.langora.writting.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "writing_topics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String languageId;

    String code;

    String name;

    String description;

    Integer displayOrder;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
