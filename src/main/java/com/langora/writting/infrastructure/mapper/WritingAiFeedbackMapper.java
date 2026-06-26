package com.langora.writting.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.langora.writting.domain.entity.WritingAiFeedback;
import com.langora.writting.dto.response.WritingAiFeedbackResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WritingAiFeedbackMapper {

    WritingAiFeedbackResponse toResponse(WritingAiFeedback entity);
}
