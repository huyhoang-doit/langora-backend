package com.langora.user.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.langora.user.domain.entity.UserLearningGoal;
import com.langora.user.dto.request.UserLearningGoalUpdateRequest;
import com.langora.user.dto.response.UserLearningGoalResponse;
import com.langora.user.infrastructure.mapper.UserLearningGoalMapper;
import com.langora.user.repository.UserLearningGoalRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserLearningGoalService {

    UserLearningGoalRepository userLearningGoalRepository;
    UserLearningGoalMapper userLearningGoalMapper;

    @Transactional(readOnly = true)
    public UserLearningGoalResponse getLearningGoal(String userId) {
        UserLearningGoal goal =
                userLearningGoalRepository.findByUserId(userId).orElseGet(() -> createDefaultLearningGoal(userId));
        return userLearningGoalMapper.toResponse(goal);
    }

    @Transactional
    public UserLearningGoalResponse updateLearningGoal(String userId, UserLearningGoalUpdateRequest request) {
        UserLearningGoal goal =
                userLearningGoalRepository.findByUserId(userId).orElseGet(() -> createDefaultLearningGoal(userId));

        if (request.getTargetLanguageId() != null) goal.setTargetLanguageId(request.getTargetLanguageId());
        if (request.getGoalTitle() != null) goal.setGoalTitle(request.getGoalTitle());
        if (request.getTargetWords() != null) goal.setTargetWords(request.getTargetWords());
        if (request.getTargetLessons() != null) goal.setTargetLessons(request.getTargetLessons());
        if (request.getTargetDays() != null) goal.setTargetDays(request.getTargetDays());
        if (request.getStartDate() != null) goal.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) goal.setEndDate(request.getEndDate());

        goal.setUpdatedAt(OffsetDateTime.now());
        userLearningGoalRepository.save(goal);

        return userLearningGoalMapper.toResponse(goal);
    }

    private UserLearningGoal createDefaultLearningGoal(String userId) {
        return userLearningGoalRepository.save(UserLearningGoal.builder()
                .userId(userId)
                .completed(false)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build());
    }
}
