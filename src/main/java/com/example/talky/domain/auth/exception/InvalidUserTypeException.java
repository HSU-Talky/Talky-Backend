package com.example.talky.domain.auth.exception;

import com.example.talky.global.exception.BaseException;

public class InvalidUserTypeException extends BaseException {
    public InvalidUserTypeException() {
        super(AuthErrorCode.INVALID_USER_TYPE_400);
    }
}
