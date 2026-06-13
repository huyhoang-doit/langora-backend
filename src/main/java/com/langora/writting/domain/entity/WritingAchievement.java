package com.langora.writting.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "writing_achievements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingAchievement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String sessionId;

    String achievementCode;

    String achievementName;

    String description;

    java.time.OffsetDateTime awardedAt;

    java.time.OffsetDateTime createdAt;
}
