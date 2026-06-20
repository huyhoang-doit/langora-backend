package com.langora.writting.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.langora.learning.domain.repository.LanguageRepository;
import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;
import com.langora.writting.domain.entity.WritingContentType;
import com.langora.writting.domain.repository.WritingContentTypeRepository;
import com.langora.writting.domain.repository.WritingExercisesRepository;
import com.langora.writting.dto.request.WritingContentTypeRequest;
import com.langora.writting.dto.response.WritingContentTypeResponse;
import com.langora.writting.infrastructure.mapper.WritingContentTypeMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WritingContentTypeService {

    WritingContentTypeRepository writingContentTypeRepository;
    WritingExercisesRepository writingExercisesRepository;
    LanguageRepository languageRepository;
    WritingContentTypeMapper writingContentTypeMapper;

    public Page<WritingContentTypeResponse> getContentTypes(String langId, String search, int page, int size) {
        if (!languageRepository.existsById(langId)) {
            throw new AppException(ErrorCode.LANGUAGE_NOT_FOUND);
        }
        Pageable pageable =
                PageRequest.of(page - 1, size, Sort.by("displayOrder").ascending());

        String searchQuery = (search != null && !search.trim().isEmpty()) ? search.trim() : "";
        Page<WritingContentType> typePage = writingContentTypeRepository.findByLanguageIdAndSearch(langId, searchQuery, pageable);

        List<WritingContentTypeResponse> responses = typePage.getContent().stream()
                .map(writingContentTypeMapper::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, typePage.getTotalElements());
    }

    public WritingContentTypeResponse getContentType(String id) {
        WritingContentType type = writingContentTypeRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_CONTENT_TYPE_NOT_FOUND));
        return writingContentTypeMapper.toResponse(type);
    }

    public WritingContentTypeResponse createContentType(String langId, WritingContentTypeRequest request) {
        if (!languageRepository.existsById(langId)) {
            throw new AppException(ErrorCode.LANGUAGE_NOT_FOUND);
        }
        WritingContentType type = writingContentTypeMapper.toEntity(request);
        type.setLanguageId(langId);
        type.setCreatedAt(OffsetDateTime.now());
        type.setUpdatedAt(OffsetDateTime.now());

        type = writingContentTypeRepository.save(type);
        return writingContentTypeMapper.toResponse(type);
    }

    @org.springframework.transaction.annotation.Transactional
    public void bulkImportContentTypes(String langId, List<WritingContentTypeRequest> requests) {
        if (!languageRepository.existsById(langId)) {
            throw new AppException(ErrorCode.LANGUAGE_NOT_FOUND);
        }
        if (requests == null || requests.isEmpty()) {
            return;
        }

        java.util.Set<com.langora.writting.domain.enums.WritingContentTypeCode> processedCodes = new java.util.HashSet<>();

        for (int i = 0; i < requests.size(); i++) {
            WritingContentTypeRequest req = requests.get(i);
            int rowNumber = i + 1;

            if (req.getCode() == null) {
                throw new AppException(ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Mã loại nội dung không được để trống.");
            }
            if (req.getName() == null || req.getName().trim().isEmpty()) {
                throw new AppException(ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Tên loại nội dung không được để trống.");
            }
            if (req.getDisplayOrder() == null) {
                throw new AppException(ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Thứ tự hiển thị không được để trống.");
            }
            if (processedCodes.contains(req.getCode())) {
                throw new AppException(ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Mã loại nội dung '" + req.getCode() + "' bị trùng lặp trong file.");
            }
            processedCodes.add(req.getCode());
        }

        // Clean table
        writingContentTypeRepository.deleteByLanguageId(langId);

        List<WritingContentType> toSave = new java.util.ArrayList<>();
        for (WritingContentTypeRequest req : requests) {
            WritingContentType type = writingContentTypeMapper.toEntity(req);
            type.setLanguageId(langId);
            type.setCreatedAt(OffsetDateTime.now());
            type.setUpdatedAt(OffsetDateTime.now());
            toSave.add(type);
        }

        writingContentTypeRepository.saveAll(toSave);
    }

    public WritingContentTypeResponse updateContentType(String id, WritingContentTypeRequest request) {
        WritingContentType type = writingContentTypeRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_CONTENT_TYPE_NOT_FOUND));

        writingContentTypeMapper.updateEntityFromRequest(request, type);
        type.setUpdatedAt(OffsetDateTime.now());

        type = writingContentTypeRepository.save(type);
        return writingContentTypeMapper.toResponse(type);
    }

    public void deleteContentType(String id) {
        WritingContentType type = writingContentTypeRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_CONTENT_TYPE_NOT_FOUND));

        if (writingExercisesRepository.existsByContentTypeId(id)) {
            throw new AppException(ErrorCode.WRITING_CONTENT_TYPE_IN_USE);
        }

        writingContentTypeRepository.delete(type);
    }
}
