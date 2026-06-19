package com.langora.learning.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.langora.learning.domain.entity.Level;
import com.langora.learning.dto.request.LevelRequest;
import com.langora.learning.dto.response.LevelResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LevelMapper {

    LevelResponse toResponse(Level entity);

    Level toEntity(LevelRequest request);

    void updateEntityFromRequest(LevelRequest request, @MappingTarget Level entity);
}
