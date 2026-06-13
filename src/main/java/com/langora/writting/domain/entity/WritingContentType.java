package com.langora.writting.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import com.langora.writting.domain.enums.WritingContentTypeCode;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "writing_content_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingContentType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    WritingContentTypeCode code;

    String name;

    String iconUrl;

    String description;

    Integer displayOrder;

    java.time.OffsetDateTime createdAt;

    java.time.OffsetDateTime updatedAt;
}
