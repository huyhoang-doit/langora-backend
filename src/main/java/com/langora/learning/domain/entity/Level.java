package com.langora.learning.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "levels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String languageId;

    String code;

    String name;

    Integer orderIndex;

    String description;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
