package com.langora.writting.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.langora.writting.domain.entity.WritingExercises;
import com.langora.writting.dto.request.WritingExerciseRequest;
import com.langora.writting.dto.response.WritingExerciseResponse;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WritingExerciseMapper {

    WritingExercises toEntity(WritingExerciseRequest request);

    @Mapping(target = "levelName", ignore = true)
    @Mapping(target = "contentTypeName", ignore = true)
    @Mapping(target = "topicName", ignore = true)
    WritingExerciseResponse toResponse(WritingExercises entity);

    void updateEntityFromRequest(WritingExerciseRequest request, @MappingTarget WritingExercises entity);
}
