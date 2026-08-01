package com.langora.ai.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.langora.ai.domain.entity.AiApiKey;
import com.langora.ai.dto.response.AiApiKeyResponse;

@Mapper(componentModel = "spring")
public interface AiApiKeyMapper {

    @Mapping(target = "mask", ignore = true)
    AiApiKeyResponse toResponse(AiApiKey entity);
}
