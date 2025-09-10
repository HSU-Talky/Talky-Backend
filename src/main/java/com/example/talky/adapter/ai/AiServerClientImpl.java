package com.example.talky.adapter.ai;

import com.example.talky.adapter.ai.response.RecommendationResponse;
import com.example.talky.global.ai.FastApiClient;
import com.example.talky.global.ai.dto.AiRcmdRes;
import com.example.talky.global.ai.dto.ToAiReq;
import com.example.talky.global.response.SuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class AiServerClientImpl implements AiServerClient {

    private final RestTemplate restTemplate;
    private final FastApiClient client;
    @Value("${ai.server.url}")
    private String aiServerUrl;

    @Override
    public AiRcmdRes getAiRcmdSentences(ToAiReq req) {
        AiRcmdRes fastApiResponse = client.getAiRcmd(req);

        if (fastApiResponse.getCategory() == null || fastApiResponse.getRecommendedSentences().isEmpty()) {
            log.info("AI 서버 응답이 비어있는 관계로 실패했습니다.");
            throw new RuntimeException("서버에서 알 수 없는 예외가 발생했습니다.");
        } else {
            return fastApiResponse;
        }
    }

    @Override
    public String transcribe(MultipartFile audioFile) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
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

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        URI uri = UriComponentsBuilder
                .fromUriString(aiServerUrl) // FastAPI 서버 띄워지는 주소로 변경
                .path("/stt/transcribe") // 해당 path로 수정 요망
                .encode()
                .build()
                .toUri();

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(uri, entity, JsonNode.class);

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            return responseEntity.getBody().get("transcription").asText();
        } else {
            log.error("AI 서버 STT 호출 실패 : {}", responseEntity.getStatusCode());
            throw new RuntimeException("AI 서버 STT 호출 실패");
        }
    }
}