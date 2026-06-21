package com.langora.writting.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.langora.writting.domain.entity.WritingExercises;

@Repository
public interface WritingExercisesRepository extends JpaRepository<WritingExercises, String> {
    boolean existsByLevelId(String levelId);

    boolean existsByContentTypeId(String contentTypeId);

    boolean existsByTopicId(String topicId);

    @Query("SELECT w FROM WritingExercises w WHERE w.languageId = :languageId AND "
            + "(:search IS NULL OR :search = '' OR LOWER(w.title) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(w.summary) LIKE LOWER(CONCAT('%', :search, '%'))) AND "
            + "(:levelId IS NULL OR :levelId = '' OR w.levelId = :levelId) AND "
            + "(:topicId IS NULL OR :topicId = '' OR w.topicId = :topicId) AND "
            + "(:contentTypeId IS NULL OR :contentTypeId = '' OR w.contentTypeId = :contentTypeId)")
    Page<WritingExercises> findByFilters(
            @Param("languageId") String languageId,
            @Param("search") String search,
            @Param("levelId") String levelId,
            @Param("topicId") String topicId,
            @Param("contentTypeId") String contentTypeId,
            Pageable pageable);

    @Query("SELECT w.id FROM WritingExercises w WHERE w.languageId = :languageId")
    List<String> findIdsByLanguageId(@Param("languageId") String languageId);

    void deleteByLanguageId(String languageId);
}
