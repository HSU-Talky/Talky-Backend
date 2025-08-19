package com.example.talky.domain.statics.service;

import com.example.talky.domain.favorites.entity.Favorite;
import com.example.talky.domain.favorites.repository.FavoriteRepository;
import com.example.talky.domain.recommendation.entity.Speech;
import com.example.talky.domain.recommendation.repository.SpeechRepository;
import com.example.talky.domain.statics.web.dto.StaticsRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaticsServiceImpl implements StaticsService {

    private final SpeechRepository speechRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public StaticsRes getNormalUsersStatics(Long normalId) {
        List<Speech> pickSpeech = speechRepository.findAllByNormalUserId(normalId);
        LocalDateTime lastDay = LocalDateTime.now().minusDays(6).toLocalDate().atStartOfDay();

        Map<LocalDate, Long> howManyUsedCount = pickSpeech.stream()
                .filter(s -> s.getCreatedAt().isBefore(lastDay))
                .collect(Collectors.groupingBy(s -> s.getCreatedAt().toLocalDate(), Collectors.counting()));
        List<Favorite> top5Favorites = favoriteRepository.findTop5ByNormalUserIdOrderByCountDesc(normalId);
        Map<String, Long> usedPlace = pickSpeech.stream()
                .filter(s -> s.getCreatedAt().toLocalDate().isEqual(LocalDateTime.now().toLocalDate()))
                .collect(Collectors.groupingBy(Speech::getPlace, Collectors.counting()));
        Map<String, Long> usedWhen = pickSpeech.stream()
                .filter(s -> s.getCreatedAt().toLocalDate().isEqual(LocalDateTime.now().toLocalDate()))
                .collect(Collectors.groupingBy(
                        s -> getTimeOfDayCategory(
                                s.getCreatedAt()
                        ), Collectors.counting()
                ));
        return new StaticsRes(
                howManyUsedCount,
                top5Favorites,
                usedPlace,
                usedWhen
        );
    }

    private static String getTimeOfDayCategory(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();

        // 21:00 이후 이거나(OR) 05:00 이전
        if (time.isAfter(LocalTime.of(21, 0).minusNanos(1)) || time.isBefore(LocalTime.of(5, 0))) {
            return "밤/새벽";
        } else if (time.isBefore(LocalTime.of(11, 0))) {
            return "오전";
        } else if (time.isBefore(LocalTime.of(17, 0))) {
            return "낮";
        } else { // 17:00 ~ 21:00
            return "저녁";
        }
    }
}
