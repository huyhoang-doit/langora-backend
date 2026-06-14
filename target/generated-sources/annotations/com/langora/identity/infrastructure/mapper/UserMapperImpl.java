package com.langora.identity.infrastructure.mapper;

import com.langora.identity.domain.entity.LoginHistory;
import com.langora.identity.domain.entity.User;
import com.langora.identity.dto.response.LoginHistoryResponse;
import com.langora.identity.dto.response.UserResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Homebrew)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.email( user.getEmail() );
        userResponse.status( user.getStatus() );
        userResponse.emailVerified( user.getEmailVerified() );
        userResponse.lastLoginAt( user.getLastLoginAt() );
        userResponse.createdAt( user.getCreatedAt() );

        return userResponse.build();
    }

    @Override
    public LoginHistoryResponse toLoginHistoryResponse(LoginHistory loginHistory) {
        if ( loginHistory == null ) {
            return null;
        }

        LoginHistoryResponse.LoginHistoryResponseBuilder loginHistoryResponse = LoginHistoryResponse.builder();

        loginHistoryResponse.id( loginHistory.getId() );
        loginHistoryResponse.sessionId( loginHistory.getSessionId() );
        loginHistoryResponse.ipAddress( loginHistory.getIpAddress() );
        loginHistoryResponse.userAgent( loginHistory.getUserAgent() );
        loginHistoryResponse.success( loginHistory.getSuccess() );
        loginHistoryResponse.failureReason( loginHistory.getFailureReason() );
        loginHistoryResponse.loggedAt( loginHistory.getLoggedAt() );

        return loginHistoryResponse.build();
    }
}
