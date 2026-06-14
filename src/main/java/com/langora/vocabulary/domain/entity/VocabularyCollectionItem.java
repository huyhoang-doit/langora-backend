package com.langora.vocabulary.domain.entity;

import java.time.*;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "vocabulary_collection_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VocabularyCollectionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String collectionId;

    String vocabularyId;

    java.time.OffsetDateTime addedAt;
}
