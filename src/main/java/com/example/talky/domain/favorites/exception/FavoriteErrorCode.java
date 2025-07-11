package com.example.talky.domain.favorites.exception;

import com.example.talky.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FavoriteErrorCode implements BaseResponseCode {
    FAVORITE_SENTENCE_INVALID_403("FAVORITE_SENTENCE_INVALID_403", 403, "잘못된 즐겨찾기 문장입니다");

    private final String code;
    private final int httpStatus;
    private final String message;
}
