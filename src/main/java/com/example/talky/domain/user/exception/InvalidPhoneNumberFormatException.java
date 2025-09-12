package com.example.talky.domain.user.exception;

import com.example.talky.global.exception.BaseException;

public class InvalidPhoneNumberFormatException extends BaseException {
    public InvalidPhoneNumberFormatException() {
        super(UserErrorCode.INVALID_PHONE_NUMBER_FORMAT_400);
    }
}
