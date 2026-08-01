package com.langora.ai.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.langora.ai.domain.entity.AiPrompt;
import com.langora.ai.domain.repository.AiPromptRepository;
import com.langora.ai.dto.request.AiPromptRequest;
import com.langora.ai.dto.request.AiPromptStatusRequest;
import com.langora.ai.dto.response.AiPromptResponse;
import com.langora.ai.infrastructure.mapper.AiPromptMapper;
import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AiPromptService {

    AiPromptRepository aiPromptRepository;
    AiPromptMapper aiPromptMapper;

    public List<AiPromptResponse> getPrompts() {
        return aiPromptRepository.findAll().stream()
                .map(aiPromptMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AiPromptResponse createPrompt(AiPromptRequest request) {
        if (aiPromptRepository.existsByField(request.getField())) {
            throw new AppException(ErrorCode.AI_PROMPT_FIELD_EXISTS);
        }

        AiPrompt prompt = AiPrompt.builder()
                .name(request.getName())
                .field(request.getField())
                .systemPrompt(request.getSystemPrompt())
                .apiKeyId(request.getApiKeyId())
                .active(request.getActive())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        prompt = aiPromptRepository.save(prompt);
        return aiPromptMapper.toResponse(prompt);
    }

    @Transactional
    public void bulkImportPrompts(java.util.List<AiPromptRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return;
        }

        java.util.Set<String> processedFields = new java.util.HashSet<>();

        for (int i = 0; i < requests.size(); i++) {
            AiPromptRequest req = requests.get(i);
            int rowNumber = i + 1;

            if (req.getField() == null || req.getField().trim().isEmpty()) {
                throw new AppException(
                        ErrorCode.BULK_IMPORT_FAILED,
                        "Lỗi dòng " + rowNumber + ": Mã định danh (field) không được để trống.");
            }
            if (processedFields.contains(req.getField())) {
                throw new AppException(
                        ErrorCode.BULK_IMPORT_FAILED,
                        "Lỗi dòng " + rowNumber + ": Mã định danh '" + req.getField() + "' bị trùng lặp trong file.");
            }

            if (aiPromptRepository.existsByField(req.getField())) {
                throw new AppException(
                        ErrorCode.BULK_IMPORT_FAILED,
                        "Lỗi dòng " + rowNumber + ": Mã định danh '" + req.getField() + "' đã tồn tại trên hệ thống.");
            }
            processedFields.add(req.getField());
        }

        java.util.List<AiPrompt> toSave = new java.util.ArrayList<>();
        for (AiPromptRequest req : requests) {
            AiPrompt prompt = AiPrompt.builder()
                    .name(req.getName())
                    .field(req.getField())
                    .systemPrompt(req.getSystemPrompt())
                    .apiKeyId(req.getApiKeyId())
                    .active(req.getActive() != null ? req.getActive() : true)
                    .createdAt(OffsetDateTime.now())
                    .updatedAt(OffsetDateTime.now())
                    .build();
            toSave.add(prompt);
        }

        aiPromptRepository.saveAll(toSave);
    }

    public AiPromptResponse updatePrompt(String id, AiPromptRequest request) {
        AiPrompt prompt =
                aiPromptRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AI_PROMPT_NOT_FOUND));

        if (!prompt.getField().equals(request.getField()) && aiPromptRepository.existsByField(request.getField())) {
            throw new AppException(ErrorCode.AI_PROMPT_FIELD_EXISTS);
        }

        prompt.setName(request.getName());
        prompt.setField(request.getField());
        prompt.setSystemPrompt(request.getSystemPrompt());
        prompt.setApiKeyId(request.getApiKeyId());
        prompt.setActive(request.getActive());
        prompt.setUpdatedAt(OffsetDateTime.now());

        prompt = aiPromptRepository.save(prompt);
        return aiPromptMapper.toResponse(prompt);
    }

    public AiPromptResponse updateStatus(String id, AiPromptStatusRequest request) {
        AiPrompt prompt =
                aiPromptRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AI_PROMPT_NOT_FOUND));

        prompt.setActive(request.getActive());
        prompt.setUpdatedAt(OffsetDateTime.now());

        prompt = aiPromptRepository.save(prompt);
        return aiPromptMapper.toResponse(prompt);
    }
}
