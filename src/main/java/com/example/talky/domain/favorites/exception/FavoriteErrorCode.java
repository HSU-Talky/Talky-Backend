package com.example.talky.domain.favorites.exception;

import com.example.talky.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FavoriteErrorCode implements BaseResponseCode {
    FAVORITE_SENTENCE_UNAUTHORIZED_401("FAVORITE_SENTENCE_UNAUTHORIZED_401", 401, "인증 세션이 만료되었으므로, 재발급해주시기 바랍니다."),
    FAVORITE_SENTENCE_NOT_FOUND_404("FAVORITE_SENTENCE_NOT_FOUND_404", 404, "해당 즐겨찾기를 조회할 수 없습니다."),
    FAVORITE_SENTENCE_FORBIDDEN_403("FAVORITE_SENTENCE_FORBIDDEN_403", 403, "해당 즐겨찾기에 대한 접근 권한이 없습니다."),
    FAVORITE_SENTENCE_CONFLICT_409("FAVORITE_SENTENCE_CONFLICT_409", 409, "이미 존재하는 즐겨찾기입니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}
