package com.langora.ai.service;

import java.time.OffsetDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.langora.ai.domain.entity.AiApiKey;
import com.langora.ai.domain.repository.AiApiKeyRepository;
import com.langora.ai.domain.repository.AiPromptRepository;
import com.langora.ai.dto.request.AiApiKeyRequest;
import com.langora.ai.dto.request.AiApiKeyStatusRequest;
import com.langora.ai.dto.response.AiApiKeyResponse;
import com.langora.ai.infrastructure.mapper.AiApiKeyMapper;
import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AiApiKeyService {

    AiApiKeyRepository aiApiKeyRepository;
    AiPromptRepository aiPromptRepository;
    AiApiKeyMapper aiApiKeyMapper;

    public Page<AiApiKeyResponse> getApiKeys(String search, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<AiApiKey> keys = aiApiKeyRepository.findBySearch(search, pageable);
        return keys.map(this::mapToResponseWithMask);
    }

    public AiApiKeyResponse createApiKey(AiApiKeyRequest request) {
        AiApiKey key = AiApiKey.builder()
                .provider(request.getProvider())
                .rawKey(request.getRawKey())
                .usage(request.getUsage())
                .rank(request.getRank())
                .active(request.getActive())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        key = aiApiKeyRepository.save(key);
        return mapToResponseWithMask(key);
    }

    @Transactional
    public void bulkImportApiKeys(java.util.List<AiApiKeyRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return;
        }

        for (int i = 0; i < requests.size(); i++) {
            AiApiKeyRequest req = requests.get(i);
            int rowNumber = i + 1;
            if (req.getProvider() == null || req.getProvider().trim().isEmpty()) {
                throw new AppException(
                        ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Provider không được để trống.");
            }
        }

        java.util.List<AiApiKey> toSave = new java.util.ArrayList<>();
        for (AiApiKeyRequest req : requests) {
            AiApiKey key = AiApiKey.builder()
                    .provider(req.getProvider())
                    .rawKey(req.getRawKey())
                    .usage(req.getUsage())
                    .rank(req.getRank())
                    .active(req.getActive() != null ? req.getActive() : true)
                    .createdAt(OffsetDateTime.now())
                    .updatedAt(OffsetDateTime.now())
                    .build();
            toSave.add(key);
        }

        aiApiKeyRepository.saveAll(toSave);
    }

    public AiApiKeyResponse updateApiKey(String id, AiApiKeyRequest request) {
        AiApiKey key =
                aiApiKeyRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AI_API_KEY_NOT_FOUND));

        key.setProvider(request.getProvider());

        if (StringUtils.hasText(request.getRawKey())) {
            key.setRawKey(request.getRawKey());
        }

        key.setUsage(request.getUsage());
        key.setRank(request.getRank());
        key.setActive(request.getActive());
        key.setUpdatedAt(OffsetDateTime.now());

        key = aiApiKeyRepository.save(key);
        return mapToResponseWithMask(key);
    }

    public AiApiKeyResponse updateStatus(String id, AiApiKeyStatusRequest request) {
        AiApiKey key =
                aiApiKeyRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AI_API_KEY_NOT_FOUND));

        key.setActive(request.getActive());
        key.setUpdatedAt(OffsetDateTime.now());

        key = aiApiKeyRepository.save(key);
        return mapToResponseWithMask(key);
    }

    public void deleteApiKey(String id) {
        AiApiKey key =
                aiApiKeyRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AI_API_KEY_NOT_FOUND));

        if (aiPromptRepository.existsByApiKeyId(id)) {
            throw new AppException(ErrorCode.AI_API_KEY_IN_USE);
        }

        aiApiKeyRepository.delete(key);
    }

    private AiApiKeyResponse mapToResponseWithMask(AiApiKey entity) {
        AiApiKeyResponse response = aiApiKeyMapper.toResponse(entity);
        response.setMask(maskKey(entity.getRawKey()));
        return response;
    }

    private String maskKey(String rawKey) {
        if (!StringUtils.hasText(rawKey)) {
            return "";
        }
        if (rawKey.length() <= 12) {
            return "***"; // Too short to safely mask with revealing characters
        }
        String start = rawKey.substring(0, 8);
        String end = rawKey.substring(rawKey.length() - 4);
        return start + "..." + end;
    }
}
