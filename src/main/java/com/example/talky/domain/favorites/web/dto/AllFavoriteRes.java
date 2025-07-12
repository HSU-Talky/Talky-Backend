package com.example.talky.domain.favorites.web.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AllFavoriteRes(List<AllFavorite> favorites) {
    public record AllFavorite(
            String sentence,
            Integer count,
            LocalDateTime createAt
    ) {}
}
