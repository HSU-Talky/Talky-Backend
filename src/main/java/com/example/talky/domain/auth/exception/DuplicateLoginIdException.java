package com.example.talky.domain.auth.exception;

import com.example.talky.global.exception.BaseException;

public class DuplicateLoginIdException extends BaseException {
    public DuplicateLoginIdException() {
        super(AuthErrorCode.USER_DUPLICATE_LOGINID_409);
    }
}
