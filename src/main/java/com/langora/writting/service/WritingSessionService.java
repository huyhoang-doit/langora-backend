package com.langora.writting.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.langora.writting.dto.request.WritingBulkSentenceAnswerRequest;
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

        List<WritingSentenceAnswer> answers = writingSentenceAnswerRepository.findBySessionId(sessionId);
        if (!answers.isEmpty()) {
            BigDecimal totalScore = BigDecimal.ZERO;
            BigDecimal grammarScore = BigDecimal.ZERO;
            BigDecimal vocabScore = BigDecimal.ZERO;
            BigDecimal fluencyScore = BigDecimal.ZERO;
            BigDecimal accuracyScore = BigDecimal.ZERO;

            for (WritingSentenceAnswer a : answers) {
                totalScore = totalScore.add(a.getAiScore() != null ? a.getAiScore() : BigDecimal.ZERO);
                grammarScore = grammarScore.add(a.getGrammarScore() != null ? a.getGrammarScore() : BigDecimal.ZERO);
                vocabScore = vocabScore.add(a.getVocabularyScore() != null ? a.getVocabularyScore() : BigDecimal.ZERO);
                fluencyScore = fluencyScore.add(a.getFluencyScore() != null ? a.getFluencyScore() : BigDecimal.ZERO);
                accuracyScore = accuracyScore.add(a.getAccuracyScore() != null ? a.getAccuracyScore() : BigDecimal.ZERO);
            }

            BigDecimal count = BigDecimal.valueOf(answers.size());
            session.setTotalScore(totalScore.divide(count, 2, RoundingMode.HALF_UP));
            session.setGrammarScore(grammarScore.divide(count, 2, RoundingMode.HALF_UP));
            session.setVocabularyScore(vocabScore.divide(count, 2, RoundingMode.HALF_UP));
            session.setFluencyScore(fluencyScore.divide(count, 2, RoundingMode.HALF_UP));
            session.setAccuracyScore(accuracyScore.divide(count, 2, RoundingMode.HALF_UP));
        }

        session.setStatus(WritingSessionStatus.COMPLETED);
        session.setSubmittedAt(OffsetDateTime.now());
        session.setCompletedAt(OffsetDateTime.now());
        session.setUpdatedAt(OffsetDateTime.now());

        session = writingSessionRepository.save(session);
        return writingSessionMapper.toResponse(session);
    }

    public WritingAiFeedbackResponse submitSentenceAnswer(String sessionId, WritingSentenceAnswerRequest request) {
        WritingSession session = writingSessionRepository
                .findById(sessionId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        WritingSentenceAnswer answer = WritingSentenceAnswer.builder()
                .sessionId(sessionId)
                .sentenceId(request.getSentenceId())
                .userAnswer(request.getUserAnswer())
                .aiScore(BigDecimal.valueOf(8.5))
                .grammarScore(BigDecimal.valueOf(8.0))
                .vocabularyScore(BigDecimal.valueOf(9.0))
                .fluencyScore(BigDecimal.valueOf(8.5))
                .accuracyScore(BigDecimal.valueOf(9.0))
                .submittedAt(OffsetDateTime.now())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        answer = writingSentenceAnswerRepository.save(answer);

        session.setCurrentSentenceOrder(session.getCurrentSentenceOrder() + 1);
        writingSessionRepository.save(session);

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
        mockFeedback = writingAiFeedbackRepository.save(mockFeedback);
        
        WritingAiFeedbackResponse response = writingAiFeedbackMapper.toResponse(mockFeedback);
        response.setSessionId(sessionId);
        response.setSentenceId(request.getSentenceId());
        
        return response;
    }

    public WritingSessionResponse submitBulkSentenceAnswers(String sessionId, WritingBulkSentenceAnswerRequest request) {
        WritingSession session = writingSessionRepository
                .findById(sessionId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        if (request.getAnswers() != null) {
            for (WritingSentenceAnswerRequest answerReq : request.getAnswers()) {
                submitSentenceAnswer(sessionId, answerReq);
            }
        }

        if (request.isSubmitSession()) {
            return submitSession(sessionId);
        }

        return writingSessionMapper.toResponse(session);
    }

    public List<WritingAiFeedbackResponse> getAiFeedbacks(String sessionId) {
        List<WritingSentenceAnswer> answers = writingSentenceAnswerRepository.findBySessionId(sessionId);
        return answers.stream()
                .flatMap(answer -> writingAiFeedbackRepository.findByAnswerId(answer.getId()).stream())
                .map(writingAiFeedbackMapper::toResponse)
                .collect(Collectors.toList());
    }
}
