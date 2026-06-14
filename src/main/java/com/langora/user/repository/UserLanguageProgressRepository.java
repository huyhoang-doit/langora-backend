package com.langora.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.user.domain.entity.UserLanguageProgress;

@Repository
public interface UserLanguageProgressRepository extends JpaRepository<UserLanguageProgress, String> {
    List<UserLanguageProgress> findByUserId(String userId);
}
