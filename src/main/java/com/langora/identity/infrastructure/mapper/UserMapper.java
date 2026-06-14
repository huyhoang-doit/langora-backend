package com.langora.identity.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.langora.identity.domain.entity.LoginHistory;
import com.langora.identity.domain.entity.User;
import com.langora.identity.dto.response.LoginHistoryResponse;
import com.langora.identity.dto.response.UserResponse;

@Mapper
public interface UserMapper {
    UserResponse toUserResponse(User user);

    LoginHistoryResponse toLoginHistoryResponse(LoginHistory loginHistory);
}
