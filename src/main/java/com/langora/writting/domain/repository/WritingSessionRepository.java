package com.langora.writting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.writting.domain.entity.WritingSession;

@Repository
public interface WritingSessionRepository extends JpaRepository<WritingSession, String> {
    // Basic queries if needed, e.g. findByUserId
    java.util.List<WritingSession> findByUserId(String userId);
}
