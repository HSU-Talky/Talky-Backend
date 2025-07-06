package com.example.talky.global.response;

import com.example.talky.global.response.code.BaseResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@RequiredArgsConstructor
public class BaseResponse {
    private final Boolean isSuccess;

    private final String code;

    private final String message;

    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    // 정적 팩토리 메소드
    // 객체의 생성을 담당하는 클래스 매소드
    public static BaseResponse of(Boolean isSuccess, BaseResponseCode baseCode) {
        return new BaseResponse(isSuccess, baseCode.getCode(), baseCode.getMessage());
    }

    public static BaseResponse of(Boolean isSuccess, BaseResponseCode baseCode, String message) {
        return new BaseResponse(isSuccess, baseCode.getCode(), message);
    }

    public static BaseResponse of(Boolean isSuccess, String code, String message) {
        return new BaseResponse(isSuccess, code, message);
    }
}
