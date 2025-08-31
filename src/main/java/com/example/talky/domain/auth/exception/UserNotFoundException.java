package com.example.talky.domain.auth.exception;

import com.example.talky.global.exception.BaseException;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(AuthErrorCode.USER_NOT_FOUND_404);
    }
}

