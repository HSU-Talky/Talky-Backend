package com.example.talky.domain.favorites.web.dto;

public record CreateFavoriteRes(
        Long favoriteId,
        String sentence
) {
}
