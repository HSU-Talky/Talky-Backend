package com.example.talky.domain.user.exception;

import com.example.talky.global.exception.BaseException;

public class InvalidEmergencyTargetException extends BaseException {
    public InvalidEmergencyTargetException() {
        super(UserErrorCode.INVALID_EMERGENCY_TARGET_400);
    }
}
