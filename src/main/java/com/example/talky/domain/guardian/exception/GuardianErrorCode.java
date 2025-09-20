package com.example.talky.domain.guardian.exception;

import com.example.talky.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GuardianErrorCode implements BaseResponseCode {

    INVALID_CONNECTION_CODE_400("INVALID_CONNECTION_CODE_400", 400, "연결 코드가 올바르지 않습니다. 다시 확인해주세요."),
    NOT_CONNECTED_USER_400("NOT_CONNECTED_USER_400", 400, "연결되지 않은 사용자입니다."),

    ALREADY_CONNECTED_409("ALREADY_CONNECTED_409", 409, "이미 연결된 사용자입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
