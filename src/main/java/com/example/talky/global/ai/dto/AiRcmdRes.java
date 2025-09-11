package com.example.talky.global.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AI 서버로부터 받는 추천 응답 DTO.
 * AI 서버의 RecommendationResponse와 매핑됩니다.
 */
@Getter
@NoArgsConstructor
public class AiRcmdRes {
    private String category; // 메인 카테고리 (예: 첫 번째 키워드)
    private String sttMessage; // AI 서버에서 음성 파일로부터 변환된 STT 메시지

    @JsonProperty("recommended_sentences")
    private List<RcmdSentence> recommendedSentences; // 추천 문장 리스트

    @Getter
    @NoArgsConstructor
    public static class RcmdSentence {
        private Long id; // 문장 ID
        private String text; // 문장 텍스트
    }
}
