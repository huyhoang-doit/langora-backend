package com.langora.writting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.writting.domain.entity.WritingAchievement;

@Repository
public interface WritingAchievementRepository extends JpaRepository<WritingAchievement, String> {
    java.util.List<WritingAchievement> findBySessionIdIn(java.util.List<String> sessionIds);
}
