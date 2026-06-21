package com.langora.writting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.writting.domain.entity.WritingExerciseSentence;

@Repository
public interface WritingExerciseSentenceRepository extends JpaRepository<WritingExerciseSentence, String> {

    void deleteByExerciseId(String exerciseId);

    void deleteByExerciseIdIn(java.util.List<String> exerciseIds);
}
