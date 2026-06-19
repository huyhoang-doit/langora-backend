package com.langora.writting.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<WritingTopicResponse> getTopics(String langId, int page, int size) {
        if (!languageRepository.existsById(langId)) {
            throw new AppException(ErrorCode.LANGUAGE_NOT_FOUND);
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<WritingTopic> topicPage = writingTopicRepository.findByLanguageId(langId, pageable);

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
