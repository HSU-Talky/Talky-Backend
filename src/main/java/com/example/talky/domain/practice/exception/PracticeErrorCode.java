package com.example.talky.domain.practice.exception;

import com.example.talky.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PracticeErrorCode implements BaseResponseCode {
    PRACTICE_INVALID_HTTP_PARAM("PRACTICE_INVALID_HTTP_PARAM_400", 400, "요청하신 파라미터 값이 잘못되었습니다."),
    PRACTICE_UNAUTHORIZED_401("PRACTICE_UNAUTHORIZED_401", 401, "인증 세션이 만료되었으므로, 재발급해주시기 바랍니다."),
    PRACTICE_FORBIDDEN_403("PRACTICE_FORBIDDEN_403", 403, "해당 즐겨찾기에 대한 접근 권한이 없습니다."),;

    private final String code;
    private final int httpStatus;
    private final String message;

}
