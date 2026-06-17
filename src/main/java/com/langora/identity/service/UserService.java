package com.langora.identity.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.langora.identity.domain.entity.Role;
import com.langora.identity.domain.entity.User;
import com.langora.identity.domain.entity.UserRole;
import com.langora.identity.domain.enums.UserStatus;
import com.langora.identity.dto.request.UserCreationRequest;
import com.langora.identity.dto.request.UserPasswordUpdateRequest;
import com.langora.identity.dto.request.UserRoleAssignRequest;
import com.langora.identity.dto.request.UserStatusUpdateRequest;
import com.langora.identity.dto.response.LoginHistoryResponse;
import com.langora.identity.dto.response.UserResponse;
import com.langora.identity.infrastructure.mapper.UserMapper;
import com.langora.identity.repository.LoginHistoryRepository;
import com.langora.identity.repository.RoleRepository;
import com.langora.identity.repository.UserRepository;
import com.langora.identity.repository.UserRoleRepository;
import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;
import com.langora.user.domain.entity.UserLanguageProgress;
import com.langora.user.domain.entity.UserProfile;
import com.langora.user.repository.UserLanguageProgressRepository;
import com.langora.user.repository.UserProfileRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    RoleRepository roleRepository;
    LoginHistoryRepository loginHistoryRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    UserProfileRepository userProfileRepository;
    UserLanguageProgressRepository userLanguageProgressRepository;

    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = User.builder()
                .email(request.getEmail())
                .userCode(generateNextUserCode())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .status(UserStatus.ACTIVE)
                .emailVerified(false)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        user = userRepository.save(user);

        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            // Validate that all roles exist
            for (String roleId : request.getRoleIds()) {
                if (!roleRepository.existsById(roleId)) {
                    throw new AppException(ErrorCode.INVALID_KEY); // ROLE_NOT_FOUND
                }
            }

            final String userId = user.getId();
            List<UserRole> newRoles = request.getRoleIds().stream()
                    .map(roleId ->
                            UserRole.builder().userId(userId).roleId(roleId).build())
                    .toList();

            userRoleRepository.saveAll(newRoles);
        }

        userProfileRepository.save(UserProfile.builder()
                .userId(user.getId())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build());

        userLanguageProgressRepository.save(UserLanguageProgress.builder()
                .userId(user.getId())
                .totalLearnedWords(0)
                .totalMasteredWords(0)
                .totalLessonsCompleted(0)
                .totalStudyMinutes(0)
                .currentStreak(0)
                .longestStreak(0)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build());

        return userMapper.toUserResponse(user);
    }

    public Page<UserResponse> getUsers(String search, UserStatus status, String role, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        // Clean up inputs for the query
        String searchQuery = (search != null && !search.trim().isEmpty()) ? search.trim() : "";
        String roleQuery = (role != null && !role.trim().isEmpty() && !role.equalsIgnoreCase("ALL")) ? role.trim() : "";

        Page<User> userPage = userRepository.findUsersWithFilters(searchQuery, status, roleQuery, pageable);

        List<UserResponse> responses =
                userPage.getContent().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
        enrichUserResponses(responses);
        return new org.springframework.data.domain.PageImpl<>(responses, pageable, userPage.getTotalElements());
    }

    public UserResponse getUser(String id) {
        UserResponse response = userRepository
                .findById(id)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)); // USER_NOT_FOUND
        enrichUserResponses(List.of(response));
        return response;
    }

    public List<Role> getUserRoles(String id) {
        if (!userRepository.existsById(id)) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
        List<UserRole> userRoles = userRoleRepository.findByUserId(id);
        List<String> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        return roleRepository.findAllById(roleIds);
    }

    private void enrichUserResponses(List<UserResponse> responses) {
        if (responses.isEmpty()) return;

        List<String> userIds = responses.stream().map(UserResponse::getId).collect(Collectors.toList());

        List<UserProfile> profiles = userProfileRepository.findByUserIdIn(userIds);
        var profileMap = profiles.stream()
                .filter(p -> p.getFullName() != null)
                .collect(Collectors.toMap(UserProfile::getUserId, UserProfile::getFullName));

        // Fetch UserRoles
        List<UserRole> userRoles = userRoleRepository.findByUserIdIn(userIds);
        List<String> allRoleIds =
                userRoles.stream().map(UserRole::getRoleId).distinct().collect(Collectors.toList());
        var roleMap =
                roleRepository.findAllById(allRoleIds).stream().collect(Collectors.toMap(Role::getId, Role::getCode));

        var userRoleMap = userRoles.stream()
                .collect(Collectors.groupingBy(
                        UserRole::getUserId,
                        Collectors.mapping(ur -> roleMap.get(ur.getRoleId()), Collectors.toList())));

        for (UserResponse response : responses) {
            response.setFullName(profileMap.getOrDefault(response.getId(), null));
            response.setRoles(userRoleMap.getOrDefault(response.getId(), List.of()));
        }
    }

    @Transactional
    public UserResponse updateStatus(String id, UserStatusUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        user.setStatus(request.getStatus());
        user.setUpdatedAt(OffsetDateTime.now());

        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse updatePassword(String id, UserPasswordUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(OffsetDateTime.now());

        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Transactional
    public void assignRoles(String id, UserRoleAssignRequest request) {
        if (!userRepository.existsById(id)) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        // Validate that all roles exist
        for (String roleId : request.getRoleIds()) {
            if (!roleRepository.existsById(roleId)) {
                throw new AppException(ErrorCode.INVALID_KEY); // ROLE_NOT_FOUND
            }
        }

        // Remove old roles
        List<UserRole> existingRoles = userRoleRepository.findByUserId(id);
        userRoleRepository.deleteAll(existingRoles);

        // Assign new roles
        List<UserRole> newRoles = request.getRoleIds().stream()
                .map(roleId -> UserRole.builder().userId(id).roleId(roleId).build())
                .toList();

        userRoleRepository.saveAll(newRoles);
    }

    public List<LoginHistoryResponse> getLoginHistory(String id) {
        if (!userRepository.existsById(id)) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
        return loginHistoryRepository.findByUserIdOrderByLoggedAtDesc(id).stream()
                .map(userMapper::toLoginHistoryResponse)
                .toList();
    }

    private synchronized String generateNextUserCode() {
        String maxCode = userRepository.findMaxUserCode();
        long nextNum = 1;
        if (maxCode != null && maxCode.startsWith("US")) {
            try {
                nextNum = Long.parseLong(maxCode.substring(2)) + 1;
            } catch (NumberFormatException ignored) {
            }
        }
        return String.format("US%07d", nextNum);
    }
}
