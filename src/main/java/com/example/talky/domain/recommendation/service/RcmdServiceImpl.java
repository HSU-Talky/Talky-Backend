package com.example.talky.domain.recommendation.service;

import com.example.talky.adapter.ai.AiServerClient;
import com.example.talky.domain.favorites.entity.Favorite;
import com.example.talky.domain.favorites.exception.FavoriteNorFoundException;
import com.example.talky.domain.favorites.repository.FavoriteRepository;
import com.example.talky.domain.recommendation.repository.ConversationRepository;
import com.example.talky.domain.recommendation.repository.SpeechRepository;
import com.example.talky.domain.recommendation.web.dto.GetContextReq;
import com.example.talky.global.ai.dto.ToAiReq;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RcmdServiceImpl implements RcmdService {

    private final AiServerClient aiServerClient;
    private final FavoriteRepository favoriteRepository;
    private final ConversationRepository conversationRepository; // AI 추적용
    private final SpeechRepository speechRepository; // 별도 API용

    @Transactional
    @Override
    public ResponseEntity getAiRcmd(GetContextReq req) {
        // FIXME
        // 현재는 더미값으로 normalId 1번을 저장했음.
        Long normalId = 1L;
        String choose = req.getChoose();

        // choose == null -> drop all record(새로운 user 대화 이력 테이블 사용)
        if(choose == null) {
            conversationRepository.deleteAllByNormalUserId(normalId);
        }

        // 현재 클라이언트가 선택한 문장이 즐겨찾기에 있는 문장이라면,
        // 즐겨찾기의 count 값을 1 증가 시킴.
        if (favoriteIsPresent(normalId, choose)) {
            Favorite favorite = favoriteRepository
                    .findByNormalUserIdAndSentence(normalId, choose)
                    .orElseThrow(FavoriteNorFoundException::new);
            favorite.increseCount();
            favoriteRepository.save(favorite);
        }

        // sentence만 파싱 count, createAt은 필요 없을듯.
        List<String> favorites = favoriteRepository
                .findAllByNormalUserId(normalId).stream()
                .map(Favorite::getSentence)
                .toList();

        /**
         * 1. keywords
         * 2. context (ex. 머리가 아파서요)
         * 3. conversation (ex. ["choose", "대화1", "대화2"]
         *  3-1. <- 여기에 choose 를 넣어야 함
         * 4. favorite (ex. ["안녕하세요", "반갑습니다"])
         * 이 모든걸 AI 서버로 보내야 함
         * @Builder 쓰는게 나을듯 싶음.
         */

        List<String> conversations = req.getConversations();
        conversations.addFirst(choose);

        ToAiReq toAiReq = ToAiReq.builder()
                .keywords(req.getKeywords())
                .context(req.getContext())
                .conversations(req.getConversations())
                .favorites(favorites)
                .build();

        // callAiServer 현재는 null로 구조 일단 짜둠.
        return aiServerClient.callAiServer(toAiReq);

        // 반환. 이후에 한번에 new DTO Res 로 리팩토링 해도 될듯.
        // 여기는 문장만 반환해주면 됨
    }

    private boolean favoriteIsPresent(Long normalId, String choose) {
        return favoriteRepository.findByNormalUserIdAndSentence(normalId, choose).isPresent();
    }
}
