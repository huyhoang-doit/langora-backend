package com.langora.writting.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;
import com.langora.writting.domain.entity.WritingAiFeedback;
import com.langora.writting.domain.entity.WritingSentenceAnswer;
import com.langora.writting.domain.entity.WritingSession;
import com.langora.writting.domain.enums.WritingSessionStatus;
import com.langora.writting.domain.repository.WritingAiFeedbackRepository;
import com.langora.writting.domain.repository.WritingSentenceAnswerRepository;
import com.langora.writting.domain.repository.WritingSessionRepository;
import com.langora.writting.dto.request.WritingSentenceAnswerRequest;
import com.langora.writting.dto.request.WritingSessionCreateRequest;
import com.langora.writting.dto.request.WritingSessionStatusUpdateRequest;
import com.langora.writting.dto.response.WritingAiFeedbackResponse;
import com.langora.writting.dto.response.WritingSessionResponse;
import com.langora.writting.infrastructure.mapper.WritingAiFeedbackMapper;
import com.langora.writting.infrastructure.mapper.WritingSessionMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WritingSessionService {

    WritingSessionRepository writingSessionRepository;
    WritingSentenceAnswerRepository writingSentenceAnswerRepository;
    WritingAiFeedbackRepository writingAiFeedbackRepository;
    WritingSessionMapper writingSessionMapper;
    WritingAiFeedbackMapper writingAiFeedbackMapper;

    public WritingSessionResponse createSession(String userId, WritingSessionCreateRequest request) {
        WritingSession session = WritingSession.builder()
                .userId(userId)
                .exerciseId(request.getExerciseId())
                .status(WritingSessionStatus.IN_PROGRESS)
                .currentSentenceOrder(1)
                .startedAt(OffsetDateTime.now())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        session = writingSessionRepository.save(session);
        return writingSessionMapper.toResponse(session);
    }

    public WritingSessionResponse getSession(String sessionId) {
        WritingSession session = writingSessionRepository
                .findById(sessionId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND)); // Use proper error code
        return writingSessionMapper.toResponse(session);
    }

    public WritingSessionResponse updateSessionStatus(String sessionId, WritingSessionStatusUpdateRequest request) {
        WritingSession session = writingSessionRepository
                .findById(sessionId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        session.setStatus(WritingSessionStatus.valueOf(request.getStatus()));
        session.setUpdatedAt(OffsetDateTime.now());
        if (WritingSessionStatus.COMPLETED.name().equals(request.getStatus())
                || WritingSessionStatus.ABANDONED.name().equals(request.getStatus())) {
            session.setCompletedAt(OffsetDateTime.now());
        }

        session = writingSessionRepository.save(session);
        return writingSessionMapper.toResponse(session);
    }

    public WritingSessionResponse submitSession(String sessionId) {
        WritingSession session = writingSessionRepository
                .findById(sessionId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        session.setStatus(WritingSessionStatus.COMPLETED);
        session.setSubmittedAt(OffsetDateTime.now());
        session.setCompletedAt(OffsetDateTime.now());
        session.setUpdatedAt(OffsetDateTime.now());

        session = writingSessionRepository.save(session);
        return writingSessionMapper.toResponse(session);
    }

    public void submitSentenceAnswer(String sessionId, WritingSentenceAnswerRequest request) {
        WritingSentenceAnswer answer = WritingSentenceAnswer.builder()
                .sessionId(sessionId)
                .sentenceId(request.getSentenceId())
                .userAnswer(request.getUserAnswer())
                .submittedAt(OffsetDateTime.now())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        writingSentenceAnswerRepository.save(answer);

        // TODO: Real AI Processing Logic should go here.
        // For now, mock a feedback response
        WritingAiFeedback mockFeedback = WritingAiFeedback.builder()
                .answerId(answer.getId())
                .overallFeedback("Excellent")
                .grammarFeedback("Good")
                .vocabularyFeedback("Good")
                .fluencyFeedback("Good fluency")
                .createdAt(OffsetDateTime.now())
                .build();
        writingAiFeedbackRepository.save(mockFeedback);
    }

    public List<WritingAiFeedbackResponse> getAiFeedbacks(String sessionId) {
        List<WritingSentenceAnswer> answers = writingSentenceAnswerRepository.findBySessionId(sessionId);
        return answers.stream()
                .flatMap(answer -> writingAiFeedbackRepository.findByAnswerId(answer.getId()).stream())
                .map(writingAiFeedbackMapper::toResponse)
                .collect(Collectors.toList());
    }
}
