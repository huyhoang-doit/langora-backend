package com.langora.writting.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.langora.learning.domain.entity.Level;
import com.langora.learning.domain.repository.LanguageRepository;
import com.langora.learning.domain.repository.LevelRepository;
import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;
import com.langora.writting.domain.entity.WritingContentType;
import com.langora.writting.domain.entity.WritingExerciseSentence;
import com.langora.writting.domain.entity.WritingExercises;
import com.langora.writting.domain.entity.WritingTopic;
import com.langora.writting.domain.repository.WritingContentTypeRepository;
import com.langora.writting.domain.repository.WritingExerciseSentenceRepository;
import com.langora.writting.domain.repository.WritingExercisesRepository;
import com.langora.writting.domain.repository.WritingTopicRepository;
import com.langora.writting.dto.request.WritingExerciseRequest;
import com.langora.writting.dto.request.WritingExerciseStatusUpdateRequest;
import com.langora.writting.dto.response.WritingExerciseResponse;
import com.langora.writting.dto.response.WritingExerciseSentenceResponse;
import com.langora.writting.infrastructure.mapper.WritingExerciseMapper;
import com.langora.writting.infrastructure.mapper.WritingExerciseSentenceMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WritingExerciseService {

    WritingExercisesRepository writingExercisesRepository;
    WritingExerciseSentenceRepository writingExerciseSentenceRepository;
    LanguageRepository languageRepository;
    LevelRepository levelRepository;
    WritingContentTypeRepository writingContentTypeRepository;
    WritingTopicRepository writingTopicRepository;
    WritingExerciseMapper writingExerciseMapper;
    WritingExerciseSentenceMapper writingExerciseSentenceMapper;

    public Page<WritingExerciseResponse> getExercises(
            String langId, String search, String levelId, String topicId, String contentTypeId, int page, int size) {
        if (!languageRepository.existsById(langId)) {
            throw new AppException(ErrorCode.LANGUAGE_NOT_FOUND);
        }
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        String searchQuery = (search != null && !search.trim().isEmpty()) ? search.trim() : "";
        Page<WritingExercises> exercisePage = writingExercisesRepository.findByFilters(
                langId, searchQuery, levelId, topicId, contentTypeId, pageable);

        if (exercisePage.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, 0);
        }

        // Fetch related entities names
        Set<String> levelIds = exercisePage.getContent().stream()
                .map(WritingExercises::getLevelId)
                .collect(Collectors.toSet());
        Set<String> typeIds = exercisePage.getContent().stream()
                .map(WritingExercises::getContentTypeId)
                .collect(Collectors.toSet());
        Set<String> topicIds = exercisePage.getContent().stream()
                .map(WritingExercises::getTopicId)
                .collect(Collectors.toSet());

        Map<String, String> levelMap =
                levelRepository.findAllById(levelIds).stream().collect(Collectors.toMap(Level::getId, Level::getName));
        Map<String, String> typeMap = writingContentTypeRepository.findAllById(typeIds).stream()
                .collect(Collectors.toMap(WritingContentType::getId, WritingContentType::getName));
        Map<String, String> topicMap = writingTopicRepository.findAllById(topicIds).stream()
                .collect(Collectors.toMap(WritingTopic::getId, WritingTopic::getName));

        List<WritingExerciseResponse> responses = exercisePage.getContent().stream()
                .map(entity -> {
                    WritingExerciseResponse res = writingExerciseMapper.toResponse(entity);
                    res.setLevelName(levelMap.get(entity.getLevelId()));
                    res.setContentTypeName(typeMap.get(entity.getContentTypeId()));
                    res.setTopicName(topicMap.get(entity.getTopicId()));
                    return res;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, exercisePage.getTotalElements());
    }

    public WritingExerciseResponse getExercise(String id) {
        WritingExercises entity = writingExercisesRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_EXERCISE_NOT_FOUND));

        WritingExerciseResponse res = writingExerciseMapper.toResponse(entity);
        levelRepository.findById(entity.getLevelId()).ifPresent(lvl -> res.setLevelName(lvl.getName()));
        writingContentTypeRepository
                .findById(entity.getContentTypeId())
                .ifPresent(type -> res.setContentTypeName(type.getName()));
        writingTopicRepository.findById(entity.getTopicId()).ifPresent(topic -> res.setTopicName(topic.getName()));

        List<WritingExerciseSentence> sentences =
                writingExerciseSentenceRepository.findByExerciseIdOrderBySentenceOrderAsc(id);
        List<WritingExerciseSentenceResponse> sentenceResponses = sentences.stream()
                .map(writingExerciseSentenceMapper::toResponse)
                .collect(Collectors.toList());
        res.setSentences(sentenceResponses);

        return res;
    }

    public WritingExerciseResponse createExercise(WritingExerciseRequest request) {
        validateForeignKeys(
                request.getLanguageId(), request.getLevelId(), request.getContentTypeId(), request.getTopicId());

        WritingExercises entity = writingExerciseMapper.toEntity(request);
        entity.setCreatedAt(OffsetDateTime.now());
        entity.setUpdatedAt(OffsetDateTime.now());

        entity = writingExercisesRepository.save(entity);
        return getExercise(entity.getId());
    }

    public WritingExerciseResponse updateExercise(String id, WritingExerciseRequest request) {
        WritingExercises entity = writingExercisesRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_EXERCISE_NOT_FOUND));

        validateForeignKeys(
                request.getLanguageId(), request.getLevelId(), request.getContentTypeId(), request.getTopicId());

        writingExerciseMapper.updateEntityFromRequest(request, entity);
        entity.setUpdatedAt(OffsetDateTime.now());

        entity = writingExercisesRepository.save(entity);
        return getExercise(entity.getId());
    }

    public WritingExerciseResponse updateStatus(String id, WritingExerciseStatusUpdateRequest request) {
        WritingExercises entity = writingExercisesRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_EXERCISE_NOT_FOUND));

        entity.setIsActive(request.getIsActive());
        entity.setUpdatedAt(OffsetDateTime.now());

        entity = writingExercisesRepository.save(entity);
        return getExercise(entity.getId());
    }

    public WritingExerciseResponse updateContent(
            String id, com.langora.writting.dto.request.WritingExerciseContentRequest request) {
        WritingExercises entity = writingExercisesRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_EXERCISE_NOT_FOUND));

        entity.setContent(request.getContent());
        entity.setUpdatedAt(OffsetDateTime.now());

        entity = writingExercisesRepository.save(entity);
        return getExercise(entity.getId());
    }

    @Transactional
    public void deleteExercise(String id) {
        WritingExercises entity = writingExercisesRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_EXERCISE_NOT_FOUND));

        writingExerciseSentenceRepository.deleteByExerciseId(id);
        writingExercisesRepository.delete(entity);
    }

    @Transactional
    public void bulkImportExercises(String langId, List<WritingExerciseRequest> requests) {
        if (!languageRepository.existsById(langId)) {
            throw new AppException(ErrorCode.LANGUAGE_NOT_FOUND);
        }
        if (requests == null || requests.isEmpty()) {
            return;
        }

        for (int i = 0; i < requests.size(); i++) {
            WritingExerciseRequest req = requests.get(i);
            int rowNumber = i + 1;

            if (req.getTitle() == null || req.getTitle().trim().isEmpty()) {
                throw new AppException(
                        ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Tên bài tập không được để trống.");
            }
            if (req.getLevelId() == null || req.getLevelId().trim().isEmpty()) {
                throw new AppException(
                        ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Cấp độ không được để trống.");
            }
            if (req.getContentTypeId() == null || req.getContentTypeId().trim().isEmpty()) {
                throw new AppException(
                        ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Loại nội dung không được để trống.");
            }
            if (req.getTopicId() == null || req.getTopicId().trim().isEmpty()) {
                throw new AppException(
                        ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Chủ đề không được để trống.");
            }

            // Dùng hàm nội bộ để check foreign keys cho từng dòng.
            // Có thể sẽ hit DB nhiều lần nếu file lớn, nhưng để đơn giản ta vẫn dùng existsById (vì Hiberate cache mức
            // L1 hoặc DB cache PK lookup rất nhanh).
            // Nếu muốn tối ưu tuyệt đối, cần cache tất cả ID từ đầu.
            validateForeignKeys(langId, req.getLevelId(), req.getContentTypeId(), req.getTopicId(), rowNumber);
        }

        // Clean tables
        List<String> existingExerciseIds = writingExercisesRepository.findIdsByLanguageId(langId);
        if (!existingExerciseIds.isEmpty()) {
            writingExerciseSentenceRepository.deleteByExerciseIdIn(existingExerciseIds);
        }
        writingExercisesRepository.deleteByLanguageId(langId);

        // Insert new
        List<WritingExercises> toSave = new java.util.ArrayList<>();
        for (WritingExerciseRequest req : requests) {
            WritingExercises entity = writingExerciseMapper.toEntity(req);
            entity.setLanguageId(langId);
            entity.setCreatedAt(OffsetDateTime.now());
            entity.setUpdatedAt(OffsetDateTime.now());
            toSave.add(entity);
        }

        writingExercisesRepository.saveAll(toSave);
    }

    private void validateForeignKeys(String languageId, String levelId, String contentTypeId, String topicId) {
        if (!languageRepository.existsById(languageId)) {
            throw new AppException(ErrorCode.LANGUAGE_NOT_FOUND);
        }
        if (!levelRepository.existsById(levelId)) {
            throw new AppException(ErrorCode.LEARNING_LEVEL_NOT_FOUND);
        }
        if (!writingContentTypeRepository.existsById(contentTypeId)) {
            throw new AppException(ErrorCode.WRITING_CONTENT_TYPE_NOT_FOUND);
        }
        if (!writingTopicRepository.existsById(topicId)) {
            throw new AppException(ErrorCode.WRITING_TOPIC_NOT_FOUND);
        }
    }

    private void validateForeignKeys(
            String languageId, String levelId, String contentTypeId, String topicId, int rowNumber) {
        if (!levelRepository.existsById(levelId)) {
            throw new AppException(ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Cấp độ không tồn tại.");
        }
        if (!writingContentTypeRepository.existsById(contentTypeId)) {
            throw new AppException(
                    ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Loại nội dung không tồn tại.");
        }
        if (!writingTopicRepository.existsById(topicId)) {
            throw new AppException(ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Chủ đề không tồn tại.");
        }
    }
}
