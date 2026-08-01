package com.langora.writting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.writting.domain.entity.WritingSentenceAnswer;

@Repository
public interface WritingSentenceAnswerRepository extends JpaRepository<WritingSentenceAnswer, String> {
    java.util.List<WritingSentenceAnswer> findBySessionId(String sessionId);
}
