package com.example.talky.domain.auth.exception;

import com.example.talky.global.exception.BaseException;

public class InvalidPasswordException extends BaseException {
    public InvalidPasswordException() {
        super(AuthErrorCode.INVALID_PASSWORD_401);
    }
}
