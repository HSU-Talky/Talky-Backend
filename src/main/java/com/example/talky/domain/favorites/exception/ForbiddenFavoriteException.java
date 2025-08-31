package com.example.talky.domain.favorites.exception;

import com.example.talky.global.exception.BaseException;

public class ForbiddenFavoriteException extends BaseException {
    public ForbiddenFavoriteException() {
        super(FavoriteErrorCode.FAVORITE_SENTENCE_FORBIDDEN_403);
    }
}
