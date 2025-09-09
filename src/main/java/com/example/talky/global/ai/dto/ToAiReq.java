package com.example.talky.global.ai.dto;

import lombok.*;

import java.util.List;

/**
 * AI 서버로 전송되는 요청 메타데이터 DTO.
 * AI 서버의 RecommendationRequest와 매핑됩니다.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ToAiReq {
    private List<String> keywords; // 대화 주제 키워드
    private String context; // 사용자의 현재 상황 설명
    private String choose; // 사용자가 선택한 이전 추천 문장
    private List<String> favorites; // 사용자의 즐겨찾기 문장 목록
    private List<String> conversation; // 누적 대화 이력 (conversations에서 이름 변경)
    private String sttMessage; // 상대방의 마지막 음성인식(STT) 메시지 (AI 서버에서 생성)
}
