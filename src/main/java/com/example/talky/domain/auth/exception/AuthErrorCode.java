package com.example.talky.domain.auth.exception;

import com.example.talky.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseResponseCode {
    USER_DUPLICATE_LOGINID_409("USER_DUPLICATE_LOGINID_409",409,"이미 존재하는 ID입니다."),
    INVALID_USER_TYPE_400("INVALID_USER_TYPE_400",400,"유효하지 않은 사용자 유형입니다."),
    USER_NOT_FOUND_404("USER_NOT_FOUND_404", 404, "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD_401("INVALID_PASSWORD_401", 401, "비밀번호가 일치하지 않습니다.");


    private final String code;
    private final int httpStatus;
    private final String message;
}
