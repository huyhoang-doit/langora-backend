package com.langora.writting.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.langora.writting.domain.entity.WritingExerciseSentence;
import com.langora.writting.dto.request.WritingExerciseSentenceRequest;
import com.langora.writting.dto.response.WritingExerciseSentenceResponse;

@Mapper(componentModel = "spring")
public interface WritingExerciseSentenceMapper {

    WritingExerciseSentenceResponse toResponse(WritingExerciseSentence entity);

    WritingExerciseSentence toEntity(WritingExerciseSentenceRequest request);

    void updateEntityFromRequest(WritingExerciseSentenceRequest request, @MappingTarget WritingExerciseSentence entity);

    default String mapListToString(java.util.List<String> list) {
        if (list == null || list.isEmpty()) return null;
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(list);
        } catch (Exception e) {
            return null;
        }
    }

    default java.util.List<String> mapStringToList(String str) {
        if (str == null || str.isEmpty()) return new java.util.ArrayList<>();
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper()
                    .readValue(str, new com.fasterxml.jackson.core.type.TypeReference<java.util.List<String>>() {});
        } catch (Exception e) {
            // Fallback to simple split if it was stored as pipe-separated in the past or in excel
            return java.util.Arrays.stream(str.split("\\|")).map(String::trim).toList();
        }
    }
}
