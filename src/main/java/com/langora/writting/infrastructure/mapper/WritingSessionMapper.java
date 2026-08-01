package com.langora.writting.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.langora.writting.domain.entity.WritingSession;
import com.langora.writting.dto.response.WritingSessionResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WritingSessionMapper {

    WritingSessionResponse toResponse(WritingSession entity);
}
