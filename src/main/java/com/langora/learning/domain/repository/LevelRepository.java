package com.langora.learning.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.learning.domain.entity.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, String> {
    Page<Level> findByLanguageId(String languageId, Pageable pageable);
}
