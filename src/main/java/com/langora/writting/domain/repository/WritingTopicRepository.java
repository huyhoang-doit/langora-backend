package com.langora.writting.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.langora.writting.domain.entity.WritingTopic;

@Repository
public interface WritingTopicRepository extends JpaRepository<WritingTopic, String> {
    Page<WritingTopic> findByLanguageId(String languageId, Pageable pageable);

    @Query("SELECT w FROM WritingTopic w WHERE w.languageId = :languageId AND "
            + "(:search = '' OR LOWER(w.name) LIKE LOWER(CONCAT('%', :search, '%')) "
            + "OR LOWER(w.code) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<WritingTopic> findByLanguageIdAndSearch(
            @Param("languageId") String languageId, @Param("search") String search, Pageable pageable);

    List<WritingTopic> findAllByLanguageIdAndCodeIn(String languageId, java.util.List<String> codes);

    void deleteByLanguageId(String languageId);
}
