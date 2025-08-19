package com.example.talky.domain.statics.service;

import com.example.talky.domain.recommendation.entity.Speech;
import com.example.talky.domain.recommendation.repository.SpeechRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaticsServiceImpl implements StaticsService {

    private final SpeechRepository speechRepository;

    @Override
    public Object getNormalUsersStatics(Long normalId) {
        List<Speech> pickSpeech = speechRepository.findAllByNormalUserId(normalId);
        LocalDateTime lastDay = LocalDateTime.now().minusDays(6);
        List<Speech> recentlyOneWeek = pickSpeech.stream()
                .filter(s -> s.getCreatedAt().isAfter(lastDay))
                .toList();

        return null;
    }
}
