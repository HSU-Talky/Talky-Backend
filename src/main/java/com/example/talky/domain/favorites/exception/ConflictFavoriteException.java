package com.example.talky.domain.favorites.exception;

import com.example.talky.global.exception.BaseException;

public class ConflictFavoriteException extends BaseException {
    public ConflictFavoriteException() {
        super(FavoriteErrorCode.FAVORITE_SENTENCE_CONFLICT_409);
    }
}
