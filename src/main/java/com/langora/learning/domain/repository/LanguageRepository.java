package com.langora.learning.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.learning.domain.entity.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {
    Optional<Language> findByCode(String code);
}
