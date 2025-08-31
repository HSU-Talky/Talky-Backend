package com.example.talky.domain.practice.exception;

import com.example.talky.global.exception.BaseException;

public class PracticeUnauthorizedException extends BaseException {
    public PracticeUnauthorizedException() {
        super(PracticeErrorCode.PRACTICE_UNAUTHORIZED_401);
    }
}
