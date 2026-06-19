package com.langora.writting.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.writting.domain.entity.WritingContentType;

@Repository
public interface WritingContentTypeRepository extends JpaRepository<WritingContentType, String> {
    Page<WritingContentType> findByLanguageId(String languageId, Pageable pageable);
}
