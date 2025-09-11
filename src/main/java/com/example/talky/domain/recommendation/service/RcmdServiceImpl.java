package com.example.talky.domain.recommendation.service;

import com.example.talky.global.ai.FastApiClient;
import com.example.talky.domain.auth.entity.NormalUser;
import com.example.talky.domain.auth.exception.UserNotFoundException;
import com.example.talky.domain.auth.repository.UserRepository;
import com.example.talky.domain.favorites.entity.Favorite;
import com.example.talky.domain.favorites.exception.FavoriteNorFoundException;
import com.example.talky.domain.favorites.repository.FavoriteRepository;
import com.example.talky.domain.recommendation.entity.Conversation;
import com.example.talky.domain.recommendation.entity.Speech;
import com.example.talky.domain.recommendation.repository.ConversationRepository;
import com.example.talky.domain.recommendation.repository.SpeechRepository;
import com.example.talky.domain.recommendation.web.dto.GetContextReq;
import com.example.talky.global.ai.dto.ToAiReq;
import com.example.talky.global.ai.dto.AiRcmdRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RcmdServiceImpl implements RcmdService {

    private final FastApiClient fastApiClient;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final ConversationRepository conversationRepository; // AI 추적용
    private final SpeechRepository speechRepository; // 별도 API용
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public AiRcmdRes getAiRcmd(MultipartFile file, String metadataJson, Long normalId) throws JsonProcessingException {
        // 메타데이터 JSON 파싱
        GetContextReq req = objectMapper.readValue(metadataJson, GetContextReq.class);
        String choose = req.getChoose();

        // choose == null -> drop all record(새로운 user 대화 이력 테이블 사용)
        if(choose == null) {
            //log.info("나 삭제될게?");
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
        }

        // 변경된 대화 기록 저장
        conversation.modify(tracking);

        // Context 3 - 데이터베이스에 저장
        conversationRepository.saveAndFlush(conversation);

        // 현재 클라이언트가 선택한 문장이 즐겨찾기에 있는 문장이라면,
        // 즐겨찾기의 count 값을 1 증가 시킴.
        if (favoriteIsPresent(normalId, choose)) {
            Favorite favorite = favoriteRepository
                    .findByNormalUserIdAndSentence(normalId, choose)
                    .orElseThrow(FavoriteNorFoundException::new);
            favorite.increaseCount();
            favoriteRepository.save(favorite);
        }

        // sentence만 파싱 count, createAt은 필요 없을듯.
        List<String> favorites = favoriteRepository
                .findAllByNormalUserId(normalId).stream()
                .map(Favorite::getSentence)
                .toList();

        // AI 서버로 보낼 요청 객체 (ToAiReq) 생성
        // sttMessage는 현재 턴에서는 AI 서버가 파일로부터 생성하므로 null로 설정
        ToAiReq toAiReq = ToAiReq.builder()
                .keywords(req.getKeywords())
                .context(req.getContext())
                .choose(choose)
                .conversation(conversation.getConversations())
                .favorites(favorites)
                .sttMessage(null)
                .build();

        // AI 서버 호출
        AiRcmdRes response = fastApiClient.getAiRecommendation(toAiReq, file);
        String category = response.getCategory();
        String sttResultFromAi = response.getSttMessage(); // AI 서버로부터 STT 결과 수신

        // AI 서버에서 받은 STT 결과를 다음 턴의 컨텍스트를 위해 대화 기록에 추가
        if (sttResultFromAi != null) {
            tracking.add(sttResultFromAi);
            conversation.modify(tracking);
            conversationRepository.saveAndFlush(conversation);
        }

        // 음성 기록 저장 (통계용)
        speechRepository.save(Speech.builder()
                        .normalUser((NormalUser) userRepository.findById(normalId)
                                .orElseThrow(UserNotFoundException::new))
                        .place(category)
                        .build());

        return response;
    }

    private boolean favoriteIsPresent(Long normalId, String choose) {
        if (choose == null) return false;
        return favoriteRepository.findByNormalUserIdAndSentence(normalId, choose).isPresent();
    }
}
