package com.langora.writting.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.writting.domain.enums.WritingLevelCode;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "writing_levels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    WritingLevelCode code;

    String name;

    String description;

    Integer displayOrder;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
