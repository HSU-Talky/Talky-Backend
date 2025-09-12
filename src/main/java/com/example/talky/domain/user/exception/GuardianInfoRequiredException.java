package com.example.talky.domain.user.exception;

import com.example.talky.global.exception.BaseException;

public class GuardianInfoRequiredException extends BaseException {
    public GuardianInfoRequiredException() {
        super(UserErrorCode.GUARDIAN_INFO_REQUIRED_400);
    }
}
