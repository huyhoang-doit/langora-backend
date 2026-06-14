package com.langora.shared.dto.response;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @Builder.Default
    boolean success = true;

    String message;

    T data;

    Object meta;

    List<ApiError> errors;

    @Builder.Default
    OffsetDateTime timestamp = OffsetDateTime.now();
}
