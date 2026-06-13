package com.langora.identity.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.identity.domain.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
