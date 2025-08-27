package com.example.talky.domain.statics.service;

import com.example.talky.domain.emergency_history.entity.EmergencyHistory;
import com.example.talky.domain.emergency_history.repository.EmergencyHistoryRepository;
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
    private final EmergencyHistoryRepository ehRepository;

    @Override
    public StaticsRes getNormalUsersStatics(Long normalId) {
        // 선택된 normal_user의 PK를 통하여 모든 발화 이력을 조회
        List<Speech> pickSpeech = speechRepository.findAllByNormalUserId(normalId, LocalDateTime.now().minusDays(7).toLocalDate());
        LocalDateTime lastDay = LocalDateTime.now().minusDays(6).toLocalDate().atStartOfDay();

        // 최근 7일간 사용한 시각을 통한 개수 카운팅
        Map<LocalDate, Long> preHowManyUsedCount = pickSpeech.stream()
                .filter(s -> s.getCreatedAt().isBefore(lastDay))
                .collect(Collectors.groupingBy(s ->
                        s.getCreatedAt().toLocalDate(), Collectors.counting()));

        // normal_user가 좋아하는 상위 5개의 즐겨찾기 조회
        List<Favorite> top5Favorites = favoriteRepository.findTop5ByNormalUserIdOrderByCountDesc(normalId);

        // 사용한 시각과 같은 날짜의 사용 장소를 카운트
        Map<String, Long> usedPlace = pickSpeech.stream()
                .filter(s -> s.getCreatedAt().toLocalDate().isEqual(LocalDateTime.now().toLocalDate()))
                .collect(Collectors.groupingBy(Speech::getPlace, Collectors.counting()));

        // 사용한 시각과 같은 날짜의 사용 시간대를 커스텀 함수로 변환
        Map<String, Long> usedWhen = pickSpeech.stream()
                .filter(s -> s.getCreatedAt().toLocalDate().isEqual(LocalDateTime.now().toLocalDate()))
                .collect(Collectors.groupingBy(
                        s -> getTimeOfDayCategory(
                                s.getCreatedAt()
                        ), Collectors.counting()
                ));

        List<EmergencyHistory> userEmergencyHistories = ehRepository.findAllByNormalId(LocalDateTime.now());
        List<StaticsRes.history> parsedEmergencyHistory = userEmergencyHistories.stream()
                .filter(s -> s.getCreatedAt().isBefore(lastDay))
                .map(h -> new StaticsRes.history(
                        h.getCreatedAt().format(DateTimeFormatter.ofPattern(
                                "MM/dd"
                        )),
                        h.getCreatedAt().format(DateTimeFormatter.ofPattern(
                                "hh:mm"
                        )),
                        h.getRoadAddress(),
                        h.getTarget()
                ))
                .toList();


        return new StaticsRes(
                howManyUsedCount,
                top5Favorites,
                usedPlace,
                usedWhen,
                parsedEmergencyHistory
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
