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
import org.springframework.transaction.annotation.Transactional;

import com.langora.learning.domain.repository.LanguageRepository;
import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;
import com.langora.writting.domain.entity.WritingTopic;
import com.langora.writting.domain.repository.WritingExercisesRepository;
import com.langora.writting.domain.repository.WritingTopicRepository;
import com.langora.writting.dto.request.WritingTopicRequest;
import com.langora.writting.dto.response.WritingTopicResponse;
import com.langora.writting.infrastructure.mapper.WritingTopicMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WritingTopicService {

    WritingTopicRepository writingTopicRepository;
    WritingExercisesRepository writingExercisesRepository;
    LanguageRepository languageRepository;
    WritingTopicMapper writingTopicMapper;

    public Page<WritingTopicResponse> getTopics(String langId, String search, int page, int size) {
        if (!languageRepository.existsById(langId)) {
            throw new AppException(ErrorCode.LANGUAGE_NOT_FOUND);
        }
        Pageable pageable =
                PageRequest.of(page - 1, size, Sort.by("displayOrder").ascending());

        String searchQuery = (search != null && !search.trim().isEmpty()) ? search.trim() : "";
        Page<WritingTopic> topicPage = writingTopicRepository.findByLanguageIdAndSearch(langId, searchQuery, pageable);

        List<WritingTopicResponse> responses = topicPage.getContent().stream()
                .map(writingTopicMapper::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, topicPage.getTotalElements());
    }

    public WritingTopicResponse getTopic(String id) {
        WritingTopic topic = writingTopicRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_TOPIC_NOT_FOUND));
        return writingTopicMapper.toResponse(topic);
    }

    public WritingTopicResponse createTopic(String langId, WritingTopicRequest request) {
        if (!languageRepository.existsById(langId)) {
            throw new AppException(ErrorCode.LANGUAGE_NOT_FOUND);
        }
        WritingTopic topic = writingTopicMapper.toEntity(request);
        topic.setLanguageId(langId);
        topic.setCreatedAt(OffsetDateTime.now());
        topic.setUpdatedAt(OffsetDateTime.now());

        topic = writingTopicRepository.save(topic);
        return writingTopicMapper.toResponse(topic);
    }

    @Transactional
    public void bulkImportTopics(String langId, List<WritingTopicRequest> requests) {
        if (!languageRepository.existsById(langId)) {
            throw new AppException(ErrorCode.LANGUAGE_NOT_FOUND);
        }
        if (requests == null || requests.isEmpty()) {
            return;
        }

        java.util.Set<String> processedCodes = new java.util.HashSet<>();

        for (int i = 0; i < requests.size(); i++) {
            WritingTopicRequest req = requests.get(i);
            int rowNumber = i + 1; // Used for error reporting

            if (req.getCode() == null || req.getCode().trim().isEmpty()) {
                throw new AppException(ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Mã chủ đề không được để trống.");
            }
            if (req.getName() == null || req.getName().trim().isEmpty()) {
                throw new AppException(ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Tên chủ đề không được để trống.");
            }
            if (processedCodes.contains(req.getCode())) {
                throw new AppException(ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Mã chủ đề '" + req.getCode() + "' bị trùng lặp trong file.");
            }
            processedCodes.add(req.getCode());
        }

        // Clean table
        writingTopicRepository.deleteByLanguageId(langId);

        List<WritingTopic> toSave = new java.util.ArrayList<>();
        for (WritingTopicRequest req : requests) {
            WritingTopic topic = writingTopicMapper.toEntity(req);
            topic.setLanguageId(langId);
            topic.setCreatedAt(OffsetDateTime.now());
            topic.setUpdatedAt(OffsetDateTime.now());
            toSave.add(topic);
        }

        writingTopicRepository.saveAll(toSave);
    }

    public WritingTopicResponse updateTopic(String id, WritingTopicRequest request) {
        WritingTopic topic = writingTopicRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_TOPIC_NOT_FOUND));

        writingTopicMapper.updateEntityFromRequest(request, topic);
        topic.setUpdatedAt(OffsetDateTime.now());

        topic = writingTopicRepository.save(topic);
        return writingTopicMapper.toResponse(topic);
    }

    public void deleteTopic(String id) {
        WritingTopic topic = writingTopicRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_TOPIC_NOT_FOUND));

        if (writingExercisesRepository.existsByTopicId(id)) {
            throw new AppException(ErrorCode.WRITING_TOPIC_IN_USE);
        }

        writingTopicRepository.delete(topic);
    }
}
