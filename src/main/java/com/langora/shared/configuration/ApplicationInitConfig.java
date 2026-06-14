package com.langora.shared.configuration;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.langora.identity.domain.entity.Permission;
import com.langora.identity.domain.entity.Role;
import com.langora.identity.domain.entity.RolePermission;
import com.langora.identity.domain.entity.User;
import com.langora.identity.domain.entity.UserRole;
import com.langora.identity.domain.enums.UserStatus;
import com.langora.identity.repository.PermissionRepository;
import com.langora.identity.repository.RolePermissionRepository;
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
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserRoleRepository userRoleRepository,
            PermissionRepository permissionRepository,
            RolePermissionRepository rolePermissionRepository) {
        return args -> {
            log.info("Checking and initializing default data (Roles, Permissions, Users)...");

            // 1. Initialize Permissions
            List<String[]> permissionData = List.of(
                    new String[] {"USERS_VIEW", "View users list and details"},
                    new String[] {"USERS_MANAGE", "Manage users (Create, Update, Delete, Suspend)"},
                    new String[] {"ROLES_VIEW", "View roles and permissions"},
                    new String[] {"ROLES_MANAGE", "Manage roles and assign permissions"},
                    new String[] {"CONTENT_VIEW", "View learning content"},
                    new String[] {"CONTENT_MANAGE", "Manage learning content (Create, Update, Delete)"});

            for (String[] p : permissionData) {
                if (permissionRepository.findByCode(p[0]).isEmpty()) {
                    permissionRepository.save(Permission.builder()
                            .code(p[0])
                            .name(p[0].replace("_", " "))
                            .description(p[1])
                            .createdAt(OffsetDateTime.now())
                            .build());
                }
            }

            // 2. Initialize Roles
            Role adminRole = roleRepository
                    .findByCode("ADMIN")
                    .orElseGet(() -> roleRepository.save(Role.builder()
                            .code("ADMIN")
                            .name("System Administrator")
                            .description("Full system access")
                            .isSystem(true)
                            .createdAt(OffsetDateTime.now())
                            .build()));

            Role memberRole = roleRepository
                    .findByCode("MEMBER")
                    .orElseGet(() -> roleRepository.save(Role.builder()
                            .code("MEMBER")
                            .name("Member User")
                            .description("Standard user access")
                            .isSystem(true)
                            .createdAt(OffsetDateTime.now())
                            .build()));

            // 3. Assign Permissions to ADMIN
            List<Permission> allPermissions = permissionRepository.findAll();
            List<RolePermission> currentAdminPermissions = rolePermissionRepository.findByRoleId(adminRole.getId());
            if (currentAdminPermissions.isEmpty()) {
                List<RolePermission> adminRolePermissions = allPermissions.stream()
                        .map(p -> RolePermission.builder()
                                .roleId(adminRole.getId())
                                .permissionId(p.getId())
                                .build())
                        .toList();
                rolePermissionRepository.saveAll(adminRolePermissions);
            }

            // 4. Assign Permissions to MEMBER (Only CONTENT_VIEW)
            List<RolePermission> currentMemberPermissions = rolePermissionRepository.findByRoleId(memberRole.getId());
            if (currentMemberPermissions.isEmpty()) {
                permissionRepository.findByCode("CONTENT_VIEW").ifPresent(p -> {
                    rolePermissionRepository.save(RolePermission.builder()
                            .roleId(memberRole.getId())
                            .permissionId(p.getId())
                            .build());
                });
            }

            // 5. Initialize Admin User
            String adminEmail = "admin@langora.com";
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User adminUser = userRepository.save(User.builder()
                        .email(adminEmail)
                        .passwordHash(passwordEncoder.encode("admin123"))
                        .status(UserStatus.ACTIVE)
                        .emailVerified(true)
                        .createdAt(OffsetDateTime.now())
                        .build());

                userRoleRepository.save(UserRole.builder()
                        .userId(adminUser.getId())
                        .roleId(adminRole.getId())
                        .assignedAt(OffsetDateTime.now())
                        .assignedBy("SYSTEM")
                        .build());

                log.warn("Admin user created! Email: {}, Password: admin123", adminEmail);
            }

            // 6. Initialize Member User
            String memberEmail = "member@langora.com";
            if (userRepository.findByEmail(memberEmail).isEmpty()) {
                User memberUser = userRepository.save(User.builder()
                        .email(memberEmail)
                        .passwordHash(passwordEncoder.encode("member123"))
                        .status(UserStatus.ACTIVE)
                        .emailVerified(true)
                        .createdAt(OffsetDateTime.now())
                        .build());

                userRoleRepository.save(UserRole.builder()
                        .userId(memberUser.getId())
                        .roleId(memberRole.getId())
                        .assignedAt(OffsetDateTime.now())
                        .assignedBy("SYSTEM")
                        .build());

                log.warn("Member user created! Email: {}, Password: member123", memberEmail);
            }
        };
    }
}
