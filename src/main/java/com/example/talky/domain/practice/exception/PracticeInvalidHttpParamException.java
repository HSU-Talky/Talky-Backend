package com.example.talky.domain.practice.exception;

import com.example.talky.global.exception.BaseException;

public class PracticeInvalidHttpParamException extends BaseException {
    public PracticeInvalidHttpParamException() {
        super(PracticeErrorCode.PRACTICE_INVALID_HTTP_PARAM);
    }
}
