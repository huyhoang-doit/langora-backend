package com.langora.writting.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.writting.domain.enums.HintType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "writing_hint_usages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingHintUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String sessionId;

    String sentenceId;

    @Enumerated(EnumType.STRING)
    HintType hintType;

    Integer creditsCost;

    java.time.OffsetDateTime usedAt;

    java.time.OffsetDateTime createdAt;
}
