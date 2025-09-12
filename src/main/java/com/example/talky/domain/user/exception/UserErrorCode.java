package com.example.talky.domain.user.exception;

import com.example.talky.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseResponseCode {
    GUARDIAN_INFO_NOT_FOUND_404("GUARDIAN_INFO_NOT_FOUND_404", 404, "보호자 정보를 찾을 수 없습니다."),
    INVALID_EMERGENCY_TARGET_400("INVALID_EMERGENCY_TARGET_400", 400, "유효하지 않은 긴급호출 대상입니다. '119' 또는 'guardian'만 허용됩니다."),
    INVALID_TTS_SPEED_400("INVALID_TTS_SPEED_400", 400, "TTS 속도는 0.5 ~ 2.0 범위여야 합니다."),
    INVALID_TTS_LANGUAGE_400("INVALID_TTS_LANGUAGE_400", 400, "지원하지 않는 TTS 언어입니다. 지원 언어: ko, en, zh, ja"),
    INVALID_TTS_GENDER_400("INVALID_TTS_GENDER_400", 400, "TTS 성별은 'male' 또는 'female'만 허용됩니다."),
    INVALID_PHONE_NUMBER_FORMAT_400("INVALID_PHONE_NUMBER_FORMAT_400", 400, "올바른 전화번호 형식이 아닙니다. (예: 01012345678)"),
    GUARDIAN_INFO_REQUIRED_400("GUARDIAN_INFO_REQUIRED_400", 400, "보호자 정보가 등록되지 않았습니다. 먼저 보호자 정보를 등록해주세요.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
