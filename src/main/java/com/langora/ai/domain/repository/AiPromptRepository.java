package com.langora.ai.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.ai.domain.entity.AiPrompt;

@Repository
public interface AiPromptRepository extends JpaRepository<AiPrompt, String> {

    Optional<AiPrompt> findByField(String field);

    boolean existsByField(String field);

    boolean existsByApiKeyId(String apiKeyId);
}
