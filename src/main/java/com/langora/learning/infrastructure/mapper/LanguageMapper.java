package com.langora.learning.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.langora.learning.domain.entity.Language;
import com.langora.learning.dto.response.LanguageResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LanguageMapper {

    LanguageResponse toResponse(Language entity);
}
