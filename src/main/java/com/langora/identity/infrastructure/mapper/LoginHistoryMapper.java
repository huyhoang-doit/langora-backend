package com.langora.identity.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.langora.identity.domain.entity.LoginHistory;
import com.langora.identity.dto.response.LoginHistoryResponse;

@Mapper(componentModel = "spring")
public interface LoginHistoryMapper {
    LoginHistoryResponse toResponse(LoginHistory loginHistory);
}
