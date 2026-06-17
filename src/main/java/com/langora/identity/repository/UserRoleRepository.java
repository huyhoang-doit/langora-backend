package com.langora.identity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.langora.identity.domain.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    List<UserRole> findByUserId(String userId);

    List<UserRole> findByUserIdIn(List<String> userIds);

    List<UserRole> findByRoleId(String roleId);

    long countByRoleId(String roleId);
}
