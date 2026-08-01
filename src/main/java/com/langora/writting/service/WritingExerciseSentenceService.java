package com.langora.writting.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.langora.shared.exception.AppException;
import com.langora.shared.exception.ErrorCode;
import com.langora.writting.domain.entity.WritingExerciseSentence;
import com.langora.writting.domain.entity.WritingExercises;
import com.langora.writting.domain.repository.WritingExerciseSentenceRepository;
import com.langora.writting.domain.repository.WritingExercisesRepository;
import com.langora.writting.dto.request.WritingExerciseSentenceRequest;
import com.langora.writting.dto.response.WritingExerciseSentenceResponse;
import com.langora.writting.infrastructure.mapper.WritingExerciseSentenceMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WritingExerciseSentenceService {

    WritingExerciseSentenceRepository writingExerciseSentenceRepository;
    WritingExercisesRepository writingExercisesRepository;
    WritingExerciseSentenceMapper writingExerciseSentenceMapper;

    public List<WritingExerciseSentenceResponse> getSentencesByExerciseId(String exerciseId, String search) {
        if (!writingExercisesRepository.existsById(exerciseId)) {
            throw new AppException(ErrorCode.WRITING_EXERCISE_NOT_FOUND);
        }

        String searchQuery = (search != null && !search.trim().isEmpty()) ? search.trim() : null;

        return writingExerciseSentenceRepository
                .findByExerciseIdAndSearchOrderBySentenceOrderAsc(exerciseId, searchQuery)
                .stream()
                .map(writingExerciseSentenceMapper::toResponse)
                .collect(Collectors.toList());
    }

    public WritingExerciseSentenceResponse getSentence(String id) {
        WritingExerciseSentence sentence = writingExerciseSentenceRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_EXERCISE_SENTENCE_NOT_FOUND));
        return writingExerciseSentenceMapper.toResponse(sentence);
    }

    @Transactional
    public WritingExerciseSentenceResponse createSentence(String exerciseId, WritingExerciseSentenceRequest request) {
        if (!writingExercisesRepository.existsById(exerciseId)) {
            throw new AppException(ErrorCode.WRITING_EXERCISE_NOT_FOUND);
        }

        WritingExerciseSentence sentence = writingExerciseSentenceMapper.toEntity(request);
        sentence.setExerciseId(exerciseId);
        sentence.setCreatedAt(OffsetDateTime.now());
        sentence.setUpdatedAt(OffsetDateTime.now());

        sentence = writingExerciseSentenceRepository.save(sentence);

        updateTotalSentences(exerciseId);

        return writingExerciseSentenceMapper.toResponse(sentence);
    }

    public WritingExerciseSentenceResponse updateSentence(String id, WritingExerciseSentenceRequest request) {
        WritingExerciseSentence sentence = writingExerciseSentenceRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_EXERCISE_SENTENCE_NOT_FOUND));

        writingExerciseSentenceMapper.updateEntityFromRequest(request, sentence);
        sentence.setUpdatedAt(OffsetDateTime.now());

        sentence = writingExerciseSentenceRepository.save(sentence);
        return writingExerciseSentenceMapper.toResponse(sentence);
    }

    @Transactional
    public void deleteSentence(String id) {
        WritingExerciseSentence sentence = writingExerciseSentenceRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_EXERCISE_SENTENCE_NOT_FOUND));

        String exerciseId = sentence.getExerciseId();
        writingExerciseSentenceRepository.delete(sentence);

        updateTotalSentences(exerciseId);
    }

    @Transactional
    public void bulkImportSentences(String exerciseId, List<WritingExerciseSentenceRequest> requests) {
        if (!writingExercisesRepository.existsById(exerciseId)) {
            throw new AppException(ErrorCode.WRITING_EXERCISE_NOT_FOUND);
        }
        if (requests == null || requests.isEmpty()) {
            return;
        }

        for (int i = 0; i < requests.size(); i++) {
            WritingExerciseSentenceRequest req = requests.get(i);
            int rowNumber = i + 1;

            if (req.getSourceText() == null || req.getSourceText().trim().isEmpty()) {
                throw new AppException(
                        ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Câu gốc không được để trống.");
            }
            if (req.getTargetText() == null || req.getTargetText().trim().isEmpty()) {
                throw new AppException(
                        ErrorCode.BULK_IMPORT_FAILED, "Lỗi dòng " + rowNumber + ": Câu dịch mẫu không được để trống.");
            }
        }

        writingExerciseSentenceRepository.deleteByExerciseId(exerciseId);

        List<WritingExerciseSentence> toSave = new java.util.ArrayList<>();
        for (WritingExerciseSentenceRequest req : requests) {
            WritingExerciseSentence sentence = writingExerciseSentenceMapper.toEntity(req);
            sentence.setExerciseId(exerciseId);
            sentence.setCreatedAt(OffsetDateTime.now());
            sentence.setUpdatedAt(OffsetDateTime.now());
            toSave.add(sentence);
        }

        writingExerciseSentenceRepository.saveAll(toSave);

        updateTotalSentences(exerciseId);
    }

    private void updateTotalSentences(String exerciseId) {
        int total = writingExerciseSentenceRepository.countByExerciseId(exerciseId);
        WritingExercises exercise = writingExercisesRepository
                .findById(exerciseId)
                .orElseThrow(() -> new AppException(ErrorCode.WRITING_EXERCISE_NOT_FOUND));
        exercise.setTotalSentences(total);
        writingExercisesRepository.save(exercise);
    }
}
