package com.langora.writting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.writting.domain.entity.WritingExercises;

@Repository
public interface WritingExercisesRepository extends JpaRepository<WritingExercises, String> {
    boolean existsByLevelId(String levelId);

    boolean existsByContentTypeId(String contentTypeId);

    boolean existsByTopicId(String topicId);
}
