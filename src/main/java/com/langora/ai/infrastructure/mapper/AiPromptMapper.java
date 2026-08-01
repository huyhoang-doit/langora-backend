package com.langora.ai.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.langora.ai.domain.entity.AiPrompt;
import com.langora.ai.dto.response.AiPromptResponse;

@Mapper(componentModel = "spring")
public interface AiPromptMapper {

    AiPromptResponse toResponse(AiPrompt entity);
}
