package com.langora.writting.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.langora.writting.domain.entity.WritingExerciseSentence;

@Repository
public interface WritingExerciseSentenceRepository extends JpaRepository<WritingExerciseSentence, String> {

    void deleteByExerciseId(String exerciseId);

    void deleteByExerciseIdIn(java.util.List<String> exerciseIds);

    List<WritingExerciseSentence> findByExerciseIdOrderBySentenceOrderAsc(String exerciseId);

    @Query("SELECT s FROM WritingExerciseSentence s WHERE s.exerciseId = :exerciseId "
            + "AND (:search IS NULL OR :search = '' OR LOWER(s.sourceText) LIKE LOWER(CONCAT('%', :search, '%')) "
            + "OR LOWER(s.targetText) LIKE LOWER(CONCAT('%', :search, '%'))) "
            + "ORDER BY s.sentenceOrder ASC")
    List<WritingExerciseSentence> findByExerciseIdAndSearchOrderBySentenceOrderAsc(
            @Param("exerciseId") String exerciseId, @Param("search") String search);

    int countByExerciseId(String exerciseId);
}
