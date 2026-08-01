package com.langora.learning.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.learning.domain.entity.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, String> {
    Page<Level> findByLanguageId(String languageId, Pageable pageable);

    @org.springframework.data.jpa.repository.Query(
            "SELECT l FROM Level l WHERE l.languageId = :languageId AND "
                    + "(:search = '' OR LOWER(l.code) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(l.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(l.description) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Level> findByLanguageIdAndSearch(
            @org.springframework.data.repository.query.Param("languageId") String languageId,
            @org.springframework.data.repository.query.Param("search") String search,
            Pageable pageable);

    void deleteByLanguageId(String languageId);
}
