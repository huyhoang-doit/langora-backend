package com.langora.identity.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.langora.identity.domain.entity.User;
import com.langora.identity.domain.entity.UserRole;
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

    public Page<User> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return userRepository.findAll(pageable);
    }

    public UserResponse getUser(String id) {
        return userRepository
                .findById(id)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY)); // USER_NOT_FOUND
    }

    @Transactional
    public UserResponse updateStatus(String id, UserStatusUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));

        user.setStatus(request.getStatus());
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
}
