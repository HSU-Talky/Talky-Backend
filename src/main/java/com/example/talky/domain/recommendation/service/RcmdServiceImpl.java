package com.example.talky.domain.recommendation.service;

import com.example.talky.adapter.ai.AiServerClient;
import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.exception.UserNotFoundException;
import com.example.talky.domain.auth.repository.UserRepository;
import com.example.talky.domain.favorites.entity.Favorite;
import com.example.talky.domain.favorites.exception.FavoriteNorFoundException;
import com.example.talky.domain.favorites.repository.FavoriteRepository;
import com.example.talky.domain.recommendation.entity.Conversation;
import com.example.talky.domain.recommendation.repository.ConversationRepository;
import com.example.talky.domain.recommendation.repository.SpeechRepository;
import com.example.talky.domain.recommendation.web.dto.GetContextReq;
import com.example.talky.global.ai.dto.ToAiReq;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RcmdServiceImpl implements RcmdService {

    private final AiServerClient aiServerClient;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository; // AI 추적용
    private final SpeechRepository speechRepository; // 별도 API용

    @Transactional
    @Override
    public ResponseEntity getAiRcmd(GetContextReq req, Long normalId) {

        /**
         * 1. choose가 비어있으면, 새로운 응답으로 간주 -> conversation 테이블 모두 Delete
         *
         * Conversation 데이터베이스 저장
         * 2-1. choose가 null -> 새로운 응답 생성 -> 클라이언트가 응답 선택 -> 다음 API로 전달 받음
         *  2-1-1. stt_message o - 상대가 먼저 말을 시작하는 경우 - stt_message만 저장
         *  2-1-2. stt_message x - 유저가 먼저 말을 시작하는 경우 - 둘 다 Null인 채로 시작
         * 2-2. choose != null -> 즐겨찾기에 찾아서, count 증가 & conversations 배열에 addFirst
         *  2-2-1. stt_message o - 상대 응답은 필수
         * 3. Conversation 테이블에 레코드 저장
         *
         * 4. AI 응답 구성
         * 5. AI 서버 호출!
         */

        // Context 1 - choose == null -> deleteAll
        String choose = req.getChoose();

        // choose == null -> drop all record(새로운 user 대화 이력 테이블 사용)
        if(choose == null) {
            log.info("나 삭제될게?");
            conversationRepository.deleteAllByNormalUser_Id(normalId);
        }

        // Context 2 Conversation 객체 만들기
        Conversation conversation = conversationRepository
                .findConversationByNormalUser_id(normalId);

        if(conversation == null) {
            conversation = Conversation.builder()
                    .normalUser((NormalUser) userRepository.findById(normalId)
                            .orElseThrow(UserNotFoundException::new))

                    .context(req.getContext())
                    .keywords(req.getKeywords())
                    .conversations(new ArrayList<>())
                    .build();
        }
        log.info("conversation={}", conversation);

        List<String> tracking = Optional.ofNullable(
                conversation.getConversations()
        ).orElse(new ArrayList<>());

        if(req.getChoose() != null && req.getSttMessage() != null) {
            tracking.addFirst(req.getChoose());
            tracking.addFirst(req.getSttMessage());
        } else if (req.getSttMessage() != null) {
            tracking.add(req.getSttMessage());
        }

        conversation.modify(tracking);

        // Context 3 - 데이터베이스에 저장
        conversationRepository.saveAndFlush(conversation);

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

        // 이전 대화 기록이 없을 시 NPE 예외 던지는 중임
        List<String> conversations = conversation.getConversations();

        ToAiReq toAiReq;
        log.info("choose={}, conversations={}", choose, conversations);
        if(choose == null && conversations == null) {
            log.info("잘못 들어옴");
            toAiReq = ToAiReq.builder()
                    .keywords(req.getKeywords())
                    .context(req.getContext())
                    .build();
        }
        else {
            log.info("잘 들어왓음");
            toAiReq = ToAiReq.builder()
                    .keywords(req.getKeywords())
                    .context(req.getContext())
                    .conversations(conversations)
                    .favorites(favorites)
                    .build();
        }
        log.info(toAiReq.toString());

        // callAiServer 현재는 null로 구조 일단 짜둠.
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(aiServerClient.callAiServer(toAiReq));

        // 반환. 이후에 한번에 new DTO Res 로 리팩토링 해도 될듯.
        // 여기는 문장만 반환해주면 됨
    }

    private boolean favoriteIsPresent(Long normalId, String choose) {
        return favoriteRepository.findByNormalUserIdAndSentence(normalId, choose).isPresent();
    }
}
