package com.langora.writting.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.langora.writting.domain.entity.WritingAchievement;
import com.langora.writting.dto.response.WritingAchievementResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WritingAchievementMapper {

    @Mapping(target = "achievementType", source = "achievementCode")
    @Mapping(target = "title", source = "achievementName")
    @Mapping(target = "unlockedAt", source = "awardedAt")
    WritingAchievementResponse toResponse(WritingAchievement entity);
}
