package com.sparta.delivery_app.common.security.exception;

import com.sparta.delivery_app.common.exception.errorcode.ErrorCode;
import com.sparta.delivery_app.common.security.errorcode.GlobalSecurityException;
import lombok.Getter;

@Getter
public class CustomSecurityException extends RuntimeException implements GlobalSecurityException {
    private final ErrorCode errorCode;
    private final String errorDescription;

    public CustomSecurityException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorDescription = errorCode.getDescription();
    }

    public CustomSecurityException(ErrorCode errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

}
