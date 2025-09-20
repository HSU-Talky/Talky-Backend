package com.example.talky.domain.statics.web.dto;


import java.util.List;

public record StaticsRes(
        List<UsedCount> howManyUsed,
        List<Favorites> top5Used,
        List<Places> usedPlace,
        List<Times> usedWhen,
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

    public record Places (
        String place,
        Long count
    ) {}

    public record Times(
            String when,
            Long count
    ) {}

    public record History(
            String date,
            String time,
            String roadAddress,
            String target
    ) {}
}
