package com.langora.writting.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.langora.writting.domain.entity.WritingContentType;
import com.langora.writting.dto.request.WritingContentTypeRequest;
import com.langora.writting.dto.response.WritingContentTypeResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WritingContentTypeMapper {

    WritingContentTypeResponse toResponse(WritingContentType entity);

    WritingContentType toEntity(WritingContentTypeRequest request);

    void updateEntityFromRequest(WritingContentTypeRequest request, @MappingTarget WritingContentType entity);
}
