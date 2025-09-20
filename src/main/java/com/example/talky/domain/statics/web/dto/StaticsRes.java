package com.example.talky.domain.statics.web.dto;

import com.example.talky.domain.favorites.entity.Favorite;

import java.util.List;
import java.util.Map;

public record StaticsRes(
        List<UsedCount> howManyUsed,
        List<Favorites> top5Used,
        Map<String, Long> usedPlace,
        Map<String, Long> usedWhen,
        List<History> histories
) {
    public record Favorites (
            Long favoriteId,
            String sentence,
            Integer count
    ) {}
    public record UsedCount(
            String date,
            Long value
    ) {}
    public record History(
            String date,
            String time,
            String roadAddress,
            String target
    ) {}
}
