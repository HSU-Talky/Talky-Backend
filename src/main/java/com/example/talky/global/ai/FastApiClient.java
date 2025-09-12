package com.example.talky.global.ai;

import com.example.talky.global.ai.dto.AiRcmdRes;
import com.example.talky.global.ai.dto.ToAiReq;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;


@Slf4j
@Component
@RequiredArgsConstructor
public class FastApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    @Value("${ai.server.url}")
    private String url;

    /**
     * @param request RcmdServiceImpl에서 가공한 FastAPI 호출용 Request DTO 객체입니다.
     * @return category - 보호자 통계에서 일반 사용자의 사용 위치, recommendedSentences - List<String>으로 AI 서버에서 추천된 문장들입니다.
     * 이것들을 DTO 객체로 반환받습니다.
     */
    public AiRcmdRes getAiRcmd(ToAiReq request) {
        HttpEntity<ToAiReq> toAiReqHttpEntity = setRcmdRequest(request);
        return restTemplate.postForEntity(url + "/recommendations", toAiReqHttpEntity, AiRcmdRes.class).getBody();
    }

    /**
     * AI 추천을 요청하는 메서드 (multipart/form-data 지원).
     * 음성 파일과 메타데이터를 함께 전송합니다.
     * 
     * @param metadata AI 서버로 전송할 메타데이터
     * @param audioFile 음성 파일 (선택사항)
     * @return AI 서버로부터 받은 추천 결과
     */
    public AiRcmdRes getAiRecommendation(ToAiReq metadata, MultipartFile audioFile) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // 1. 메타데이터를 JSON 문자열로 변환하여 추가
        try {
            String metadataJson = objectMapper.writeValueAsString(metadata);
            body.add("metadata", metadataJson);
        } catch (JsonProcessingException e) {
            log.error("메타데이터 JSON 직렬화 실패", e);
            throw new RuntimeException("메타데이터 처리 중 오류 발생", e);
        }

        // 2. 음성 파일이 있다면 추가
        if (audioFile != null && !audioFile.isEmpty()) {
            try {
                ByteArrayResource resource = new ByteArrayResource(audioFile.getBytes()) {
                    @Override
                    public String getFilename() {
                        return audioFile.getOriginalFilename();
                    }
                };
                body.add("file", resource);
            } catch (IOException e) {
                log.error("파일을 읽는 중 오류가 발생했습니다.", e);
                throw new RuntimeException("파일 처리 중 오류 발생", e);
            }
        }

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        // AI 서버 URL 구성 및 호출
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .path("/recommendations")
                .encode()
                .build()
                .toUri();

        // AI 서버로부터 응답 받기
        AiRcmdRes response = restTemplate.postForEntity(uri, entity, AiRcmdRes.class).getBody();

        // 응답 유효성 검사
        if (response == null || response.getCategory() == null || response.getRecommendedSentences() == null || response.getRecommendedSentences().isEmpty()) {
            log.info("AI 서버 응답이 비어있거나 유효하지 않습니다.");
            throw new RuntimeException("AI 서버에서 응답을 받지 못했습니다.");
        }
        
        return response;
    }

    /**
     * private 함수로, 객체 내부에서만 사용됩니다.
     * @param request RestTemplate로 요청을 보내기 위해서, Header 정보를 추가합니다.
     * @return Http Entity 객체를 반환합니다.
     */
    private HttpEntity<ToAiReq> setRcmdRequest(ToAiReq request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new HttpEntity<>(request, headers);
    }
}
