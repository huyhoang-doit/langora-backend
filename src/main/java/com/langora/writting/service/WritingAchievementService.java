package com.langora.writting.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.langora.writting.domain.entity.WritingAchievement;
import com.langora.writting.domain.entity.WritingSession;
import com.langora.writting.domain.repository.WritingAchievementRepository;
import com.langora.writting.domain.repository.WritingSessionRepository;
import com.langora.writting.dto.response.WritingAchievementResponse;
import com.langora.writting.infrastructure.mapper.WritingAchievementMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WritingAchievementService {

    WritingAchievementRepository writingAchievementRepository;
    WritingSessionRepository writingSessionRepository;
    WritingAchievementMapper writingAchievementMapper;

    public List<WritingAchievementResponse> getMyAchievements(String userId) {
        List<String> sessionIds = writingSessionRepository.findByUserId(userId).stream()
                .map(WritingSession::getId)
                .collect(Collectors.toList());
        if (sessionIds.isEmpty()) return Collections.emptyList();

        List<WritingAchievement> achievements = writingAchievementRepository.findBySessionIdIn(sessionIds);
        return achievements.stream()
                .map(a -> {
                    WritingAchievementResponse res = writingAchievementMapper.toResponse(a);
                    res.setUserId(userId);
                    return res;
                })
                .collect(Collectors.toList());
    }
}
