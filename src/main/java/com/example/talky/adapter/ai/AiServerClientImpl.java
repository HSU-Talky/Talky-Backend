package com.example.talky.adapter.ai;

import com.example.talky.global.ai.dto.ToAiReq;
import com.example.talky.global.ai.dto.AiRcmdRes;
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

/**
 * AI 서버와의 실제 HTTP 통신을 담당하는 클라이언트 구현체.
 * RestTemplate을 사용하여 multipart/form-data 요청을 보냅니다.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AiServerClientImpl implements AiServerClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; // JSON 직렬화/역직렬화용

    @Value("${ai.server.url}")
    private String aiServerUrl;

    @Override
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
                .fromUriString(aiServerUrl)
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
}
