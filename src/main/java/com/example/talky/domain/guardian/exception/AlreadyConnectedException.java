package com.example.talky.domain.guardian.exception;

import com.example.talky.global.exception.BaseException;

public class AlreadyConnectedException extends BaseException {
    public AlreadyConnectedException() {
        super(GuardianErrorCode.ALREADY_CONNECTED_409);
    }
}
