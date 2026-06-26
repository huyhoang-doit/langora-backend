package com.langora.identity.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.langora.identity.dto.response.LoginHistoryResponse;
import com.langora.identity.infrastructure.mapper.LoginHistoryMapper;
import com.langora.identity.repository.LoginHistoryRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginHistoryService {

    LoginHistoryRepository loginHistoryRepository;
    LoginHistoryMapper loginHistoryMapper;

    @Transactional(readOnly = true)
    public List<LoginHistoryResponse> getMyLoginHistory(String userId) {
        return loginHistoryRepository.findByUserIdOrderByLoggedAtDesc(userId).stream()
                .map(loginHistoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
