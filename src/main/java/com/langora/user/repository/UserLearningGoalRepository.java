package com.langora.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.user.domain.entity.UserLearningGoal;

@Repository
public interface UserLearningGoalRepository extends JpaRepository<UserLearningGoal, String> {
    Optional<UserLearningGoal> findByUserId(String userId);
}
