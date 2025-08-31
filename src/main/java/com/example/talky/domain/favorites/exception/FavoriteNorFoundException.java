package com.example.talky.domain.favorites.exception;

import com.example.talky.global.exception.BaseException;

public class FavoriteNorFoundException extends BaseException {
    public FavoriteNorFoundException() {
        super(FavoriteErrorCode.FAVORITE_SENTENCE_NOT_FOUND_404);
    }
}
