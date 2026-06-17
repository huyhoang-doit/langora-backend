package com.langora.identity.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.langora.identity.domain.entity.User;
import com.langora.identity.domain.enums.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    @Query("SELECT MAX(u.userCode) FROM User u")
    String findMaxUserCode();

    @Query("SELECT DISTINCT u FROM User u " + "LEFT JOIN UserRole ur ON u.id = ur.userId "
            + "LEFT JOIN Role r ON ur.roleId = r.id "
            + "WHERE (:search = '' OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(u.userCode) LIKE LOWER(CONCAT('%', :search, '%'))) "
            + "AND (:status IS NULL OR u.status = :status) "
            + "AND (:role = '' OR r.code = :role)")
    Page<User> findUsersWithFilters(
            @Param("search") String search,
            @Param("status") UserStatus status,
            @Param("role") String role,
            Pageable pageable);
}
