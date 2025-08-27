package com.example.talky.domain.statics.web.dto;

import com.example.talky.domain.favorites.entity.Favorite;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record StaticsRes(
        Map<LocalDate, Long> howManyUsed,
        List<Favorite> top5Used,
        Map<String, Long> usedPlace,
        Map<String, Long> usedWhen,
        List<history> histories
) {
    public record history (
            String date,
            String time,
            String roadAddress,
            String target
    ) {}
}
