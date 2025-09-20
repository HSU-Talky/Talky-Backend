package com.example.talky.domain.guardian.exception;

import com.example.talky.global.exception.BaseException;

public class NotConnectedUserException extends BaseException {
    public NotConnectedUserException() {
        super(GuardianErrorCode.NOT_CONNECTED_USER_400);
    }
}
