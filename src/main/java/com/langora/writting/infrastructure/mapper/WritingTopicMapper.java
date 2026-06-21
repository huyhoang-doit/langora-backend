package com.langora.writting.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.langora.writting.domain.entity.WritingTopic;
import com.langora.writting.dto.request.WritingTopicRequest;
import com.langora.writting.dto.response.WritingTopicResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WritingTopicMapper {

    WritingTopicResponse toResponse(WritingTopic entity);

    WritingTopic toEntity(WritingTopicRequest request);

    void updateEntityFromRequest(WritingTopicRequest request, @MappingTarget WritingTopic entity);
}
