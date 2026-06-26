package com.langora.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.identity.domain.entity.UserSession;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, String> {}
