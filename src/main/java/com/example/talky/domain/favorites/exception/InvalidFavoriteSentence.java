package com.example.talky.domain.favorites.exception;

import com.example.talky.global.exception.BaseException;

public class InvalidFavoriteSentence extends BaseException {
    public InvalidFavoriteSentence() {
        super(FavoriteErrorCode.FAVORITE_SENTENCE_INVALID_403);
    }
}
