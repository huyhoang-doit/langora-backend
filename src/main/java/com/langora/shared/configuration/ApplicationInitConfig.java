package com.langora.shared.configuration;

import java.time.OffsetDateTime;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.langora.identity.domain.entity.Role;
import com.langora.identity.domain.entity.User;
import com.langora.identity.domain.entity.UserRole;
import com.langora.identity.domain.enums.UserStatus;
import com.langora.identity.repository.RoleRepository;
import com.langora.identity.repository.UserRepository;
import com.langora.identity.repository.UserRoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(
            UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        return args -> {
            log.info("Checking and initializing default admin account...");

            // Create ADMIN role if not exists
            Role adminRole = roleRepository.findByCode("ADMIN").orElseGet(() -> {
                Role role = Role.builder()
                        .code("ADMIN")
                        .name("System Administrator")
                        .description("Full system access")
                        .isSystem(true)
                        .createdAt(OffsetDateTime.now())
                        .build();
                return roleRepository.save(role);
            });

            // Create admin user if not exists
            String adminEmail = "admin@langora.com";
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User adminUser = User.builder()
                        .email(adminEmail)
                        .passwordHash(passwordEncoder.encode("admin123"))
                        .status(UserStatus.ACTIVE)
                        .emailVerified(true)
                        .createdAt(OffsetDateTime.now())
                        .build();

                adminUser = userRepository.save(adminUser);

                // Assign ADMIN role to the user
                UserRole userRole = UserRole.builder()
                        .userId(adminUser.getId())
                        .roleId(adminRole.getId())
                        .assignedAt(OffsetDateTime.now())
                        .assignedBy("SYSTEM")
                        .build();
                userRoleRepository.save(userRole);

                log.warn("Admin user has been created! Email: {}, Password: {}", adminEmail, "admin123");
            }
        };
    }
}
