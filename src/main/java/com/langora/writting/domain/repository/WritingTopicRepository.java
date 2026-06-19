package com.langora.writting.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.writting.domain.entity.WritingTopic;

@Repository
public interface WritingTopicRepository extends JpaRepository<WritingTopic, String> {
    Page<WritingTopic> findByLanguageId(String languageId, Pageable pageable);
}
