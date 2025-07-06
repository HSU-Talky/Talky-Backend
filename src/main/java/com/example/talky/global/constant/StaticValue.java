package com.example.talky.global.constant;

public class StaticValue {
    //성공
    public static final int OK = 200;
    //생성
    public static final int CREATED = 201;

    // 잘못된 요청
    public static final int BAD_REQUEST = 400;
    // 권한 없음
    public static final int UNAUTHORIZED = 401;
    // 금지됨
    public static final int FORBIDDEN = 403;
    // 찾을 수 없음
    public static final int NOT_FOUND = 404;
    // 허용되지 않는 메소드
    public static final int METHOD_NOT_ALLOWED = 405;

    // 내부 서버 오류
    public static final int INTERNAL_SERVER_ERROR = 500;
}
