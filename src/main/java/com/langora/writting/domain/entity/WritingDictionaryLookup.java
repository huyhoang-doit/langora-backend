package com.langora.writting.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "writing_dictionary_lookups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingDictionaryLookup {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String sessionId;

    String word;

    Integer lookupCount;

    java.time.OffsetDateTime lastLookupAt;

    java.time.OffsetDateTime createdAt;
}
