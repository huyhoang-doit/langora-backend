package com.langora.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // -- COMMON --
    UNCATEGORIZED_EXEPTION(9999, "Uncategorized Exception Error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(9998, "Invalid Key Error", HttpStatus.BAD_REQUEST),

    // -- IDENTITY & AUTH --
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),

    // -- USER --
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),

    // -- ROLE --
    ROLE_NOT_FOUND(2001, "Role not found", HttpStatus.NOT_FOUND),
    SYSTEM_ROLE_CANNOT_BE_MODIFIED(2002, "Cannot modify or delete a system role", HttpStatus.BAD_REQUEST),
    ROLE_IN_USE(2003, "Role is currently assigned to users and cannot be deleted", HttpStatus.BAD_REQUEST),
    ROLE_ALREADY_EXISTS(2004, "Role code already exists", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String msg, HttpStatusCode statusCode) {
        this.code = code;
        this.msg = msg;
        this.statusCode = statusCode;
    }

    private int code;
    private String msg;
    private HttpStatusCode statusCode;
}
