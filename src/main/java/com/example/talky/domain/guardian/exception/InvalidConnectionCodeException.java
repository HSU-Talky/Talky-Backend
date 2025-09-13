package com.example.talky.domain.guardian.exception;

import com.example.talky.global.exception.BaseException;

public class InvalidConnectionCodeException extends BaseException {
    public InvalidConnectionCodeException() {
        super(GuardianErrorCode.INVALID_CONNECTION_CODE_400);
    }
}
