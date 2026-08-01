package com.langora.ai.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.langora.ai.domain.entity.AiApiKey;

@Repository
public interface AiApiKeyRepository extends JpaRepository<AiApiKey, String> {

    @Query("SELECT k FROM AiApiKey k WHERE "
            + "(:search IS NULL OR :search = '' "
            + "OR LOWER(k.provider) LIKE LOWER(CONCAT('%', :search, '%')) "
            + "OR LOWER(k.usage) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<AiApiKey> findBySearch(@Param("search") String search, Pageable pageable);
}
