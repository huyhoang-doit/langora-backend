package com.langora.writting.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.langora.writting.domain.entity.WritingContentType;

@Repository
public interface WritingContentTypeRepository extends JpaRepository<WritingContentType, String> {
    Page<WritingContentType> findByLanguageId(String languageId, Pageable pageable);

    @Query("SELECT w FROM WritingContentType w WHERE w.languageId = :languageId AND "
            + "(:search = '' OR LOWER(w.name) LIKE LOWER(CONCAT('%', :search, '%')) "
            + "OR LOWER(CAST(w.code AS string)) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<WritingContentType> findByLanguageIdAndSearch(
            @Param("languageId") String languageId, @Param("search") String search, Pageable pageable);

    List<WritingContentType> findAllByLanguageIdAndCodeIn(
            String languageId, java.util.List<com.langora.writting.domain.enums.WritingContentTypeCode> codes);

    void deleteByLanguageId(String languageId);
}
