package com.example.talky.global.response.code;

public interface BaseResponseCode {
    String getCode();
    int getHttpStatus();
    String getMessage();
}
