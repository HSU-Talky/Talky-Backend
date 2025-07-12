package com.example.talky.domain.favorites.exception;

import com.example.talky.global.exception.BaseException;

public class FavoriteUnauthorizedException extends BaseException {
    public FavoriteUnauthorizedException() {
        super(FavoriteErrorCode.FAVORITE_SENTENCE_UNAUTHORIZED_401);
    }
}
