package com.langora.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.user.domain.entity.UserLearningProfile;

@Repository
public interface UserLearningProfileRepository extends JpaRepository<UserLearningProfile, String> {
    Optional<UserLearningProfile> findByUserId(String userId);
}
