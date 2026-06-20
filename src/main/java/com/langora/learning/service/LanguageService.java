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

import com.langora.learning.domain.entity.Language;
import com.langora.learning.domain.enums.LanguageStatus;
import com.langora.learning.domain.repository.LanguageRepository;
import com.langora.learning.dto.response.LanguageResponse;
import com.langora.learning.infrastructure.mapper.LanguageMapper;
import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LanguageService {

    LanguageRepository languageRepository;
    LanguageMapper languageMapper;

    public Page<LanguageResponse> getLanguages(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("code").ascending());
        Page<Language> languagePage = languageRepository.findAll(pageable);

        List<LanguageResponse> responses = languagePage.getContent().stream()
                .map(languageMapper::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, languagePage.getTotalElements());
    }

    public LanguageResponse getLanguageByCode(String code) {
        Language language =
                languageRepository.findByCode(code).orElseThrow(() -> new AppException(ErrorCode.LANGUAGE_NOT_FOUND));
        return languageMapper.toResponse(language);
    }

    public LanguageResponse toggleLanguageStatus(String id) {
        Language language =
                languageRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.LANGUAGE_NOT_FOUND));

        if (language.getStatus() == LanguageStatus.ACTIVE) {
            language.setStatus(LanguageStatus.INACTIVE);
        } else {
            language.setStatus(LanguageStatus.ACTIVE);
        }

        language.setUpdatedAt(OffsetDateTime.now());

        language = languageRepository.save(language);
        return languageMapper.toResponse(language);
    }
}
