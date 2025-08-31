package com.example.talky.domain.auth.exception;

import com.example.talky.global.exception.BaseException;

public class PermissionDeniedException extends BaseException {
    public PermissionDeniedException() {
        super(AuthErrorCode.PERMISSION_DENIED_403);
    }
}
