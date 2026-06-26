package com.langora.writting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.writting.domain.entity.WritingAiFeedback;

@Repository
public interface WritingAiFeedbackRepository extends JpaRepository<WritingAiFeedback, String> {
    java.util.List<WritingAiFeedback> findByAnswerId(String answerId);
}
