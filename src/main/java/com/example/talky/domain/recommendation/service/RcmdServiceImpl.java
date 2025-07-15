package com.example.talky.domain.recommendation.service;

import com.example.talky.adapter.ai.AiServerClient;
import com.example.talky.domain.favorites.repository.FavoriteRepository;
import com.example.talky.domain.recommendation.web.dto.GetContextReq;
import com.example.talky.domain.recommendation.web.dto.RcmdSentenceRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RcmdServiceImpl implements RcmdService {

    private final AiServerClient aiServerClient;
    private final FavoriteRepository favoriteRepository;

    @Override
    public RcmdSentenceRes getAiRcmd(GetContextReq req) {
        // 파라미터로 전달받은 Req에 즐겨찾기를 추가하여, callAiServer 메서드 호출
        // 반환
        return null;
    }
}
