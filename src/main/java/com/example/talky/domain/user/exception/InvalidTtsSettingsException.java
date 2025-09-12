package com.example.talky.domain.user.exception;

import com.example.talky.global.exception.BaseException;

public class InvalidTtsSettingsException extends BaseException {
    public InvalidTtsSettingsException() {
        super(UserErrorCode.INVALID_TTS_SPEED_400);
    }
    
    public InvalidTtsSettingsException(UserErrorCode errorCode) {
        super(errorCode);
    }
}
