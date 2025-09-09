package com.example.talky.adapter.ai;

import com.example.talky.global.ai.dto.ToAiReq;
import com.example.talky.global.ai.dto.AiRcmdRes;
import org.springframework.web.multipart.MultipartFile;

public interface AiServerClient {
    /**
     * AI 추천을 요청하는 메서드.
     * 이전 AiMetaReq 대신 ToAiReq를 사용하여 AI 서버로 메타데이터를 전달합니다.
     * 음성 파일로부터 STT는 AI 서버에서 처리하므로 sttMessage는 직접 전달하지 않습니다.
     */
    AiRcmdRes getAiRecommendation(ToAiReq metadata, MultipartFile audioFile);
}
