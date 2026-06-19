package com.langora.learning.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.langora.learning.domain.entity.Level;
import com.langora.learning.domain.repository.LanguageRepository;
import com.langora.learning.domain.repository.LevelRepository;
import com.langora.learning.dto.request.LevelRequest;
import com.langora.learning.dto.response.LevelResponse;
import com.langora.learning.infrastructure.mapper.LevelMapper;
import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LevelService {

    LevelRepository levelRepository;
    LanguageRepository languageRepository;
    LevelMapper levelMapper;

    public Page<LevelResponse> getLevelsByLanguage(String langId, int page, int size) {
        if (!languageRepository.existsById(langId)) {
            throw new AppException(ErrorCode.LANGUAGE_NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("orderIndex").ascending());
        Page<Level> levelPage = levelRepository.findByLanguageId(langId, pageable);

        List<LevelResponse> responses =
                levelPage.getContent().stream().map(levelMapper::toResponse).collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, levelPage.getTotalElements());
    }

    public LevelResponse createLevel(String langId, LevelRequest request) {
        if (!languageRepository.existsById(langId)) {
            throw new AppException(ErrorCode.LANGUAGE_NOT_FOUND);
        }

        Level level = levelMapper.toEntity(request);
        level.setLanguageId(langId);
        level.setCreatedAt(OffsetDateTime.now());
        level.setUpdatedAt(OffsetDateTime.now());

        level = levelRepository.save(level);
        return levelMapper.toResponse(level);
    }

    public LevelResponse updateLevel(String id, LevelRequest request) {
        Level level =
                levelRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.LEARNING_LEVEL_NOT_FOUND));

        levelMapper.updateEntityFromRequest(request, level);
        level.setUpdatedAt(OffsetDateTime.now());

        level = levelRepository.save(level);
        return levelMapper.toResponse(level);
    }
}
