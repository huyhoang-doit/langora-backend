package com.langora.user.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.langora.user.domain.entity.UserLearningGoal;
import com.langora.user.dto.response.UserLearningGoalResponse;

@Mapper(componentModel = "spring")
public interface UserLearningGoalMapper {
    UserLearningGoalResponse toResponse(UserLearningGoal userLearningGoal);
}
