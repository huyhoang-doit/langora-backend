package com.langora.identity.infrastructure.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DbFixRunner implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        try {
            // Find all check constraints on the users table and drop them
            // This fixes the issue where Hibernate ddl-auto: update doesn't update enum check constraints
            String sql = "SELECT conname FROM pg_constraint WHERE conrelid = 'users'::regclass AND contype = 'c'";
            jdbcTemplate.queryForList(sql, String.class).forEach(constraintName -> {
                try {
                    jdbcTemplate.execute("ALTER TABLE users DROP CONSTRAINT IF EXISTS " + constraintName);
                    log.info("Dropped constraint: {}", constraintName);
                } catch (Exception e) {
                    log.warn("Could not drop constraint {}: {}", constraintName, e.getMessage());
                }
            });
        } catch (Exception e) {
            log.warn("Could not query constraints: {}", e.getMessage());
        }
    }
}
